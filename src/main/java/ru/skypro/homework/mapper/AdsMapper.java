package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.entity.Ads;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface AdsMapper {

    Ads createAdsDtoToAdvertEntity(CreateAds createAdsDto);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "users.id", target = "author")
    AdsDto advertEntityToAdsDto(Ads ads);

    @Mapping(source = "users.firstName", target = "authorFirstName")
    @Mapping(source = "users.lastName", target = "authorLastName")
    @Mapping(source = "users.email", target = "email")
    @Mapping(source = "users.phone", target = "phone")
    @Mapping(source = "id", target = "pk")
    FullAds advertEntityToFullAdsDto(Ads ads);

    List<AdsDto> advertEntitiesToAdsDtos(List<Ads> adsList);

}