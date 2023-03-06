package ru.skypro.homework.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;

import java.util.stream.Collectors;

@Service
public class MappingUtils {

    public AdsDto mapToAdsDto(Ads entity){
        AdsDto dto = new AdsDto();
        dto.setPk(entity.getPk());
        dto.setAuthor(entity.getAuthor().getId());
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        dto.setImage(entity.getImage().toString());
        return dto;
    }

    public Ads mapToAdsEntity(AdsDto dto) {
        Ads entity = new Ads();
        entity.setPk(entity.getPk());
        entity.setAuthor(entity.getAuthor());
        entity.setPrice(entity.getPrice());
        entity.setTitle(entity.getTitle());
        entity.setImage(entity.getImage());
        return entity;


    }
}



