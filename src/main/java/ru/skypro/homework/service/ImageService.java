package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;
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
    Image uploadImage(MultipartFile imageFile, Ads ads) throws IOException;

    /**
     * Получение картинки по ID
     *
     * @param id Id картинки
     *
     * @return Images
     */
    Image getImage(long id);

    /**
     * Обновление картинки объявления
     *
     * @param imageFile      Файл картинки
     * @param authentication Файл аутентификации
     * @param adsId          ID объявления
     * @return AdsDto
     * @throws IOException
     */
    AdsDto updateImage(MultipartFile imageFile, Authentication authentication, long adsId) throws IOException;

    /**
     * Получение массива байтов(для фронта)
     * @param id
     * @return
     */
    byte[] getImageBytesArray(long id);

    /**
     * Удаление картинки по ID
     *
     * @param id Id картинки
     */
    void removeImage(long id) throws IOException;
}