package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;

public interface AdsService {

    ResponseWrapperAds getAllAds();

    AdsDto createAds(CreateAds createAdsDto);

    FullAds getAds(Integer id);

    void removeAds(Integer id);

    AdsDto updateAdvert(Integer id, AdsDto adsDto);

    ResponseWrapperAds findAds(String search);
}
