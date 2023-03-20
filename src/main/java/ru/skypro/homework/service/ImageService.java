package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;

import java.io.IOException;

/**
 * Сервис для работы с картинками
 */
public interface ImageService {

    /**
     * Сохранение картинки в БД
     *
     * @param imageFile Объект картинка
     * @return Images
     */
    Image uploadImage(MultipartFile imageFile) throws IOException;

    /**
     * Получение картинки по ID
     *
     * @param id Id картинки
     * @return Images
     */
    Image getImageById(long id);

}