package com.saket.dreamshop.service.image;

import com.saket.dreamshop.dto.ImageDto;
import com.saket.dreamshop.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(String id);

    List<ImageDto> saveImages(List<MultipartFile> file, String productId);

    void deleteImageById(String id);

    void updateImage(MultipartFile file , String imageId);

}
