package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
/**
 * Interface AdsService (advertisement/объявление).
 */
public interface AdsService {

    ResponseWrapperAds getAllAds();

    AdsDto createAds (MultipartFile image, CreateAds createAdsDto) ;

    FullAds getAds(Integer id);

    void removeAds(Integer id);

    AdsDto updateAdvert(Integer id, CreateAds createAds);

    ResponseWrapperAds findAds(String search);
}