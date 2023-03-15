package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Images;
import ru.skypro.homework.repository.ImagesRepository;
import ru.skypro.homework.service.AdsService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
@Service
public class ImagesServiceImpl {

    @Value("${path.to.images.folder}")
    private String imagesDir;

    private final ImagesRepository imagesRepository;

    private final AdsService adsService;

    public ImagesServiceImpl(ImagesRepository imagesRepository, AdsService adsService) {
        this.imagesRepository = imagesRepository;
        this.adsService = adsService;
    }

    public Images uploadImage(MultipartFile imageFile, Ads ads) throws IOException {
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
        images.setFilePath(imageFile.getName());
        images.setFileSize(imageFile.getSize());
        images.setMediaType(imageFile.getContentType());
        images.setImage(imageFile.getBytes());
        images.setAds(adsService.getAds(ads.getId()));

        return imagesRepository.save(images);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Images getImage(long id) {
        return imagesRepository.findById(id).orElseThrow(() -> new NotFoundException("Картинка с id " + id + " не найдена!"));
    }
}