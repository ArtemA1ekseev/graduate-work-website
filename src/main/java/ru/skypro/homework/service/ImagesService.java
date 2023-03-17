package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Images;
import java.io.IOException;

/**
 * Сервис для работы с картинками
 */
public interface ImagesService {

    /**
     * Сохранение картинки в БД
     *
     * @param ads Объект объявления
     * @param imageFile Объект картинка
     * @return Images
     */
    Images uploadImage(MultipartFile imageFile, Ads ads) throws IOException;

    /**
     * Получение картинки по ID
     *
     * @param id Id картинки
     *
     * @return Images
     */
    Images getImage(long id);

    /**
     * Удаление картинки по ID
     *
     * @param id Id картинки
     */
    void removeImage(long id);
}