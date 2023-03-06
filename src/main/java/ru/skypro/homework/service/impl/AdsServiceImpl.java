package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.utils.MappingUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    private AdsRepository adsRepository;
    private MappingUtils mappingUtils;

    public AdsServiceImpl(AdsRepository repository) {
        this.adsRepository = repository;
    }

    public void addAds(CreateAds createAds) {
        Ads ads = new Ads();
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        adsRepository.save(ads);
    }

    public Ads getAdsById(int adsId) {
        return adsRepository.findById(adsId).get();
    }

    @Override
    public List<AdsDto> findByTitle(String title) {
        return adsRepository.findByTitle(title).stream().map(mappingUtils::mapToAdsDto).collect(Collectors.toList());
    }


    @Override
    public List<AdsDto> findAdsByAuthor(User author) {
        return adsRepository.findAdsByAuthor(author).stream().map(mappingUtils::mapToAdsDto).collect(Collectors.toList());
    }

    @Override
    public List<AdsDto> findAdsByDescriptionContains(String part) {
        return adsRepository.findAdsByDescriptionContains(part).stream().map(mappingUtils::mapToAdsDto).collect(Collectors.toList());
    }
}
