package ru.skypro.homework.service;

import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.Image;

import java.util.Collection;

/**
 * Сервис для работы с объявлениями
 */
public interface AdsService {

    /**
     * Добавление объявления
     *
     * @param ads Объект объявления
     * @return Ads
     */
    Ads createAds(Ads ads);

    /**
     * Получение объявления по ID
     *
     * @param id ID объявления
     * @return Ads
     */
    Ads getAdsById(long id);

    /**
     * Получение всех объявлений
     *
     * @return Collection<Ads>
     */
    Collection<Ads> getAllAds();

    /**
     * Удаление объявления по ID
     *
     * @param id ID объявления
     * @return Удаленное объявление
     */
    Ads removeAdsById(long id);

    /**
     * Изменение объявления по ID
     *
     * @param updatedAds Изменённое объявление
     * @return Ads Изменённое объявление
     */
    Ads updateAds(Ads updatedAds);

    /**
     * Получение всех объявлений аутентифицированного пользователя
     *
     * @return Collection<Ads>
     */
    Collection<Ads> getAdsMe();

    /**
     * Добавление комментария к объявлению
     *
     * @param adKey      ID объявления
     * @param adsComment Объект комментария
     * @return AdsComment
     */
    AdsComment addAdsComment(long adKey, AdsComment adsComment);

    /**
     * Получение всех комментариев определённого объявления
     *
     * @param adKey ID объявления
     * @return Collection<AdsComment>
     */
    Collection<AdsComment> getAdsComments(long adKey);

    /**
     * Получение комментария по ID
     *
     * @param id    ID комментария
     * @param adKey ID объявления
     * @return Найденный комментарий
     */
    AdsComment getAdsComment(long adKey, long id);

    /**
     * Удаление комментария по ID
     *
     * @param id    ID комментария
     * @param adKey ID объявления
     * @return Удалённый комментарий
     */
    AdsComment deleteAdsComment(long adKey, long id);

    /**
     * Изменение комментария по ID
     *
     * @param id               ID комментария
     * @param adKey            ID объявления
     * @param updateAdsComment Изменённый комментарий
     * @return Изменённый комментарий
     */
    AdsComment updateAdsComment(long adKey, long id, AdsComment updateAdsComment);


    /**
     * Обновление картинки объявления
     *
     * @param ads   объявление
     * @param image новая картинка
     * @return Объявление с обновленной картинкой
     */
    Ads updateAdsImage(Ads ads, Image image);
}