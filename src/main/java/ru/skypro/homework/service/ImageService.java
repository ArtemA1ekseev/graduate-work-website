package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;

import java.io.IOException;

/**
 * Интерфейс сервиса для работы с картинками
 */
public interface ImageService {

    /**
     * Сохранение картинки в БД
     */
    Image uploadImage(MultipartFile imageFile, Ads ads) throws IOException;

    /**
     * Обновление картинки объявления
     */
    AdsDto updateImage(MultipartFile imageFile, Authentication authentication, long adsId) throws IOException;

    /**
     * Получение картинки по ID
     */
    Image getImage(long id);

    /**
     * Получение массива байтов(для фронта)
     */
    byte[] getImageBytesArray(long id);

    /**
     * Удаление картинки по ID
     */
    void removeImage(long id) throws IOException;
}