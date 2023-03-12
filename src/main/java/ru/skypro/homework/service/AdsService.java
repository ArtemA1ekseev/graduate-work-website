package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;

import java.util.Collection;
public interface AdsService {
    Ads createAds(Ads ads);
    Ads getFullAds(long id);
    Collection<Ads> getAllAds();

    boolean removeAds(long id, Authentication authentication);

    Ads updateAds(long id, Ads updatedAdsDto, Authentication authentication);

    Collection<Ads> getAdsMe();

    AdsComment addAdsComment(long ad_pk, AdsComment adsComment);

    Collection<AdsComment> getAdsComments(long ad_pk);

    AdsComment getAdsComment(long ad_pk, long id);

    boolean deleteAdsComment(long ad_pk, long id, Authentication authentication);

    AdsComment updateAdsComment(long ad_pk, long id, AdsComment toEntity, Authentication authentication);
}