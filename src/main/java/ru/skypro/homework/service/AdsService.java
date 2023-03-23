package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;

import java.io.IOException;
import java.util.List;

/**
 * Интерфейс сервиса для работы с объявлениями
 */
public interface AdsService {

    /**
     * Добавление объявления
     *
     * @param createAdsDto Объект объявления
     * @param imageFile    Картинка объявления
     * @return Ads
     */
    AdsDto createAds(CreateAdsDto createAdsDto, MultipartFile imageFile) throws IOException;

    /**
     * Получение объявления по ID
     *
     * @param id ID объявления
     * @return Ads
     */
    Ads getAds(long id);

    /**
     * Получение DTO с полной информацией об объекте
     */
    FullAdsDto getFullAdsDto(long id);

    /**
     * Получение всех объявлений
     *
     * @return Collection<Ads>
     */
    List<AdsDto> getAllAds();

    /**
     * Удаление объявления по ID
     *
     * @param id             ID объявления
     * @param authentication Аутентифицированный пользователь
     * @return Возвращает true если объявление удалено, иначе false.
     */
    boolean removeAds(long id, Authentication authentication) throws IOException;

    /**
     * Изменение объявления по ID
     *
     * @param id             ID объявления
     * @param updatedAdsDto  Изменённое объявление
     * @param authentication Аутентифицированный пользователь
     * @return Ads Изменённое объявление.
     */
    AdsDto updateAds(long id, AdsDto updatedAdsDto, Authentication authentication);

    /**
     * Получение всех объявлений аутентифицированного пользователя
     *
     * @return Collection<Ads>
     */
    List<AdsDto> getAdsMe();

    /**
     * Добавление комментария к объявлению
     *
     * @param adKey         ID объявления
     * @param adsCommentDto Объект комментария
     * @return AdsComment
     */
    AdsCommentDto addAdsComment(long adKey, AdsCommentDto adsCommentDto);

    /**
     * Получение всех комментариев определённого объявления
     *
     * @param adKey ID объявления
     * @return Collection<AdsComment>
     */
    List<AdsCommentDto> getAdsComments(long adKey);

    /**
     * Получение комментария по ID
     *
     * @param id    ID комментария
     * @param adKey ID объявления
     * @return AdsComment
     */
    AdsCommentDto getAdsComment(long adKey, long id);

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
     * @return AdsComment      Изменённый комментарий.
     */
    AdsCommentDto updateAdsComment(long adKey, long id, AdsCommentDto updateAdsComment, Authentication authentication);
}