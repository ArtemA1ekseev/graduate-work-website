package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@Transactional
@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image uploadImage(MultipartFile imageFile) throws IOException {

        Image image = new Image();

        image.setImage(imageFile.getBytes());
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setImage(imageFile.getBytes());

        return imageRepository.save(image);
    }

    @Transactional(readOnly = true)
    @Override
    public Image getImageById(long id) {

        return imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Картинка с id " + id + " не найдена!"));
    }
}