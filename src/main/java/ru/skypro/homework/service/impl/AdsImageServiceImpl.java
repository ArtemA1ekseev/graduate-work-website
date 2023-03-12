package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsImage;
import ru.skypro.homework.repository.AdsImageRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsImageService;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AdsImageServiceImpl implements AdsImageService {

    @Value("${path.to.ads.images.folder}")
    private String imageDir;
    private AdsImageRepository imageRepository;
    private AdsRepository adsRepository;

    public AdsImageServiceImpl(AdsImageRepository imageRepository, AdsRepository adsRepository) {
        this.imageRepository = imageRepository;
        this.adsRepository = adsRepository;
    }

    public AdsImage addImage(Integer adsId, MultipartFile imageFile) throws IOException {
        Ads ads = adsRepository.findById(adsId).get();
        Path path = Path.of(imageDir,imageFile.getOriginalFilename());
        if (!Files.exists(path.getParent())) {
            Files.createDirectory(path.getParent());
        }
        Files.deleteIfExists(path);
        try (
                InputStream is = imageFile.getInputStream();
                OutputStream os = Files.newOutputStream(path, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        AdsImage image = new AdsImage();
        image.setPath(path.toString());
        image.setMediaType(imageFile.getContentType());
        image.setSize(imageFile.getSize());
        image.setData(imageFile.getBytes());
        image.setAds(ads);
        imageRepository.save(image);
        return image;
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
