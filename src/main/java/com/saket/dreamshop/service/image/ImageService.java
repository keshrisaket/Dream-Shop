package com.saket.dreamshop.service.image;

import com.saket.dreamshop.dto.ImageDto;
import com.saket.dreamshop.entity.Image;
import com.saket.dreamshop.entity.Product;
import com.saket.dreamshop.exception.ResourceNotFoundException;
import com.saket.dreamshop.repository.ImageRepository;
import com.saket.dreamshop.service.product.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService {

    private final ImageRepository imagerepository;

    private final IProductService productService;


    ImageService(ImageRepository imageRepository, IProductService productService) {
        this.imagerepository = imageRepository;
        this.productService = productService;
    }


    @Override
    public Image getImageById(String id) {
        return imagerepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, String productId) {

        Product product = productService.getProductById(productId);

        List<ImageDto> saveImageDto = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));

                image.setProduct(product);

                String buildDownloadUrl = "api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();

                image.setDownloadUrl(downloadUrl);

                Image saveImage = imagerepository.save(image);
                saveImage.setDownloadUrl(buildDownloadUrl + saveImage.getId());
                imagerepository.save(saveImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(saveImage.getId());
                imageDto.setFileName(saveImage.getFileName());
                imageDto.setDownloadUrl(saveImage.getDownloadUrl());
                saveImageDto.add(imageDto);


            } catch (SQLException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }


        return saveImageDto;
    }

    @Override
    public void deleteImageById(String id) {

        imagerepository.findById(id).ifPresentOrElse(imagerepository::delete, () -> {
            throw new ResourceNotFoundException("Image not found with id: " + id);
        });

    }

    @Override
    public void updateImage(MultipartFile file, String imageId) {
        Image image = getImageById(imageId);

        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setFileType(file.getContentType());
            imagerepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
