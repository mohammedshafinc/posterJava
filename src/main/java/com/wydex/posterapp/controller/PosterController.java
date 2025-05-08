package com.wydex.posterapp.controller;

import com.wydex.posterapp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PosterController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<byte[]> handleImageUpload(@RequestParam("photo") MultipartFile photo) {
        try {
            byte[] finalImage = imageService.generatePoster(photo);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(finalImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
