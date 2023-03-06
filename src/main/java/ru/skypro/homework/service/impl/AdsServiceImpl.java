package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

@Service
public class AdsServiceImpl implements AdsService {

    private AdsRepository repository;

    public AdsServiceImpl(AdsRepository repository) {
        this.repository = repository;
    }

    public void addAds(CreateAds createAds) {
        Ads ads = new Ads();
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        repository.save(ads);
    }

    public Ads getAdsById(int adsId) {
        return repository.findById(adsId).get();
    }
}
