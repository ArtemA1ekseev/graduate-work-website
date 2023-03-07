package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.entity.Ads;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "pk", target = "id")
    default Ads createAdsDtoToAdvertEntity(CreateAds createAdsDto);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.id", target = "author")
    default AdsDto advertEntityToAdsDto(Ads ads);

    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "id", target = "pk")
    FullAds advertEntityToFullAdsDto(Ads ads);

    List<AdsDto> advertEntitiesToAdsDtos(List<Ads> adsList);

}