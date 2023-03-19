package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.Images;
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
    Ads getAds(long id);
    /**
     * Получение всех объявлений
     *
     * @return Collection<Ads>
     */
    Collection<Ads> getAllAds();
    /**
     * Удаление объявления по ID
     *
     * @param id             ID объявления
     * @param authentication Аутентифицированный пользователь
     * @return Возвращает true если объявление удалено, иначе false.
     */
    boolean removeAds(long id, Authentication authentication);
    /**
     * Изменение объявления по ID
     *
     * @param id             ID объявления
     * @param updatedAdsDto  Изменённое объявление
     * @param authentication Аутентифицированный пользователь
     * @return Ads Изменённое объявление.
     */
    Ads updateAds(long id, Ads updatedAdsDto, Authentication authentication);
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
     * @return AdsComment
     */
    AdsComment getAdsComment(long adKey, long id);
    /**
     * Удаление комментария по ID
     *
     * @param id             ID комментария
     * @param adKey          ID объявления
     * @param authentication Аутентифицированный пользователь
     * @return Возвращает true если комментарий удалён, иначе false.
     */
    boolean deleteAdsComment(long adKey, long id, Authentication authentication);
    /**
     * Изменение комментария по ID
     *
     * @param id               ID комментария
     * @param adKey            ID объявления
     * @param updateAdsComment Изменённый комментарий
     * @param authentication   Аутентифицированный пользователь
     * @return AdsComment Изменённый комментарий.
     */
    AdsComment updateAdsComment(long adKey, long id, AdsComment updateAdsComment, Authentication authentication);
    Ads updateAdsImage(Ads ads, Authentication authentication, Images image);
}