package com.saket.dreamshop.controller;


import com.saket.dreamshop.dto.ImageDto;
import com.saket.dreamshop.entity.Image;
import com.saket.dreamshop.response.ApiResponse;
import com.saket.dreamshop.service.image.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImagesController {

    private final ImageService imageService;


    ImagesController(ImageService imageService) {
        this.imageService = imageService;
    }


    @PostMapping(path = "/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam String productId) {
        try {
            List<ImageDto> images = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload success!", images));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
        }

    }


    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + image.getFileName() + "\"")
                .body(resource);

    }


    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable String imageId, @RequestBody MultipartFile file){
       Image image = imageService.getImageById(imageId);

       if (image != null){

       }

        imageService.updateImage(file, imageId);


    }


}
