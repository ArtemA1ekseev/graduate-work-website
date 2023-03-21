package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Images;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ImagesRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImagesService;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@RequiredArgsConstructor
@Service
public class ImagesServiceImpl implements ImagesService {

    @Value("${path.to.images.folder}")
    private String imagesDir;

    private final ImagesRepository imagesRepository;

    private final AdsRepository adsRepository;

    private final UserRepository userRepository;

    private final AdsMapper adsMapper;

    @Override
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
        images.setFilePath(filePath.toString());
        images.setFileSize(imageFile.getSize());
        images.setMediaType(imageFile.getContentType());
        images.setImage(imageFile.getBytes());
        images.setAds(ads);
        return imagesRepository.save(images);
    }

    @Override
    public AdsDto updateImage(MultipartFile imageFile, Authentication authentication, long adsId) throws IOException {
        Ads ads = adsRepository.findById(adsId).orElseThrow(() -> new NotFoundException("Объявление с id " + adsId + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            Images updatedImage = imagesRepository.findAllByAdsId(adsId);
            Path filePath = Path.of(updatedImage.getFilePath());
            Files.deleteIfExists(filePath);
            try (
                    InputStream is = imageFile.getInputStream();
                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
            ) {
                bis.transferTo(bos);
            }
            updatedImage.setFileSize(imageFile.getSize());
            updatedImage.setMediaType(imageFile.getContentType());
            updatedImage.setImage(imageFile.getBytes());
            ads.setImage(imagesRepository.save(updatedImage));
            adsRepository.save(ads);
        }
        return adsMapper.toDto(ads);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public Images getImage(long id) {
        return imagesRepository.findById(id).orElseThrow(() -> new NotFoundException("Картинка с id " + id + " не найдена!"));
    }

    @Override
    public byte[] getImageBytesArray(long id) {
        Images images = imagesRepository.findById(id).orElseThrow(() -> new NotFoundException("Картинка с id " + id + " не найдена!"));
        return images.getImage();
    }

    @Override
    public void removeImage(long id) throws IOException{
        Images images = imagesRepository.findById(id).orElseThrow(() -> new NotFoundException("Картинка с id " + id + " не найдена!"));
        Path filePath = Path.of(images.getFilePath());
        images.getAds().setImage(null);
        imagesRepository.deleteById(id);
        Files.deleteIfExists(filePath);
    }
}