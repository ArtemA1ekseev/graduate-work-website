package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Images;
import ru.skypro.homework.repository.ImagesRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImagesService;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@RequiredArgsConstructor
@Service
public class ImagesServiceImpl implements ImagesService {

    Logger logger = LoggerFactory.getLogger(ImagesServiceImpl.class);


    private final ImagesRepository imagesRepository;

    private final AdsService adsService;

    @Value("${path.to.images.folder}")
    private String imagesDir;

    @Override
    public Images uploadImage(MultipartFile imageFile, Ads ads) throws IOException {
        logger.info("Was invoked method for upload image");
        Path filePath = Path.of(imagesDir, "ads_" + ads.getId() + "." + getExtensions(Objects.requireNonNull(imageFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = imageFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Images images = new Images();
        images.setFilePath(filePath.toString());
        images.setFileSize(imageFile.getSize());
        images.setMediaType(imageFile.getContentType());
        images.setImage(imageFile.getBytes());
        images.setAds(adsService.getAds(ads.getId()));
        return imagesRepository.save(images);
    }

    private String getExtensions(String fileName) {
        logger.info("Was invoked method for get extensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public Images getImage(long id) {
        logger.info("Was invoked method for get image by id");
        return imagesRepository.findById(id).orElseThrow(() -> new NotFoundException("Картинка с id " + id + " не найдена!"));
    }

    @Override
    public void removeImage(long id) {
        logger.info("Was invoked method for delete image by id");
        Images images = imagesRepository.findById(id).orElseThrow(() -> new NotFoundException("Картинка с id " + id + " не найдена!"));
        images.setAds(null);
        imagesRepository.deleteById(id);
    }
}