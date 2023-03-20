package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.service.impl.AdsImageServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@CrossOrigin(value = "http://localhost:3000")
public class ImageController {
    Logger logger = LoggerFactory.getLogger(ImageController.class);


    private AdsImageServiceImpl imageService;

    public ImageController(AdsImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateAdsImage(@PathVariable int id, @RequestParam MultipartFile imageFile) throws IOException {
        logger.info("Request for update ad image");
        long imageMbSize = 10;
        if (imageFile.getSize() < imageMbSize * (1024 * 1024)) {
            imageService.addImage(id, imageFile);
            return ResponseEntity.ok().build();
        }
        logger.warn("File size exceeded");
        return ResponseEntity.badRequest().body("Размер файла не должен превышать " + imageMbSize + " Мб");
    }
}
