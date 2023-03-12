package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.entity.Images;
import ru.skypro.homework.repository.ImagesRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class ImagesServiceImpl {

    private final String imagesDir = "/images";

    //Переделать под id
    private Integer count = 1;

    private final ImagesRepository imagesRepository;

    public ImagesServiceImpl(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public Optional<Images> findImages(Long id){
        return imagesRepository.findById(id);
    }

    public void uploadImage(MultipartFile imageFile) throws IOException {
        Path filePath = Path.of(imagesDir, "image_" + count + "." + getExtensions(Objects.requireNonNull(imageFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        count++;
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
        images.setImage(imageFile.getBytes());

        imagesRepository.save(images);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Images getImage(long id) {
        return imagesRepository.findById(id).orElseThrow(() -> new NotFoundException("Картинка с id " + id + " не найдена!"));
    }

    public Images getImageAuthor(long id) {
        return imagesRepository.findByAds_id(id);
    }
}