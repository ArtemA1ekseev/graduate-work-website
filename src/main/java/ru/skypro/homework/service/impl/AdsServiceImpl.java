package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsImage;
import ru.skypro.homework.exception.AdvertNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsCommentRepository;
import ru.skypro.homework.repository.AdsImageRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsImageService;
import ru.skypro.homework.service.AdsService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final AdsCommentRepository commentRepository;
    private final AdsImageServiceImpl adsImageServiceImpl;

    private final AdsImageRepository imageRepository;
    private final AdsMapper adsMapper;

    public AdsServiceImpl(AdsRepository adsRepository, UserRepository userRepository, AdsCommentRepository commentRepository, AdsImageServiceImpl adsImageServiceImpl, AdsImageRepository imageRepository, AdsMapper adsMapper) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.adsImageServiceImpl = adsImageServiceImpl;
        this.imageRepository = imageRepository;
        this.adsMapper = adsMapper;
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        List<AdsDto> adsDtoDtoList;
        adsDtoDtoList = adsMapper.advertEntitiesToAdsDtos(adsRepository.findAllAdverts());
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsDtoDtoList.size());
        responseWrapperAds.setResults(adsDtoDtoList);
        return responseWrapperAds;
    }

    @Override
    public AdsDto createAds(MultipartFile image, CreateAds createAdsDto) {
        Ads createdAds = adsMapper.createAdsDtoToAdvertEntity(createAdsDto);
        createdAds = adsRepository.save(createdAds);

        try {
            AdsImage newImage = adsImageServiceImpl.addImage(createdAds.getId(), image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return adsMapper.advertEntityToAdsDto(createdAds);
    }

    @Override
    public void removeAds(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdvertNotFoundException::new);
        imageRepository.deleteAllByAdsId(ads.getId());
        commentRepository.deleteAllByAdsId(ads.getId());
        adsRepository.delete(ads);
    }

    @Override
    public FullAds getAds(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdvertNotFoundException::new);
        return adsMapper.advertEntityToFullAdsDto(ads);
    }

    @Override
    public AdsDto updateAdvert(Integer id, CreateAds createAds) {
        Ads ads = adsRepository.findById(id).orElseThrow(AdvertNotFoundException::new);
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        ads.setTitle(createAds.getTitle());
        adsRepository.save(ads);

        return adsMapper.advertEntityToAdsDto(ads);
    }

    @Override
    public ResponseWrapperAds findAds(String search) {
        List<AdsDto> adsDtoDtoList = adsMapper.advertEntitiesToAdsDtos(adsRepository.findAds(search));
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(adsDtoDtoList.size());
        responseWrapperAds.setResults(adsDtoDtoList);
        return responseWrapperAds;
    }
}