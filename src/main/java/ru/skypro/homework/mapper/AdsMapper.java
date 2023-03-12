package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;

@Mapper
public interface AdsMapper extends WebMapper<AdsDto, Ads> {

    @Override
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "id", source = "pk")
    Ads toEntity(AdsDto dto);
    @Override
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "pk", source = "id")
    AdsDto toDto(Ads entity);
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", source = "pk")
    Ads toEntity(CreateAdsDto dto);
    @Mapping(target = "pk", source = "id")
    CreateAdsDto createAdsToDto(Ads entity);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.email")
    FullAdsDto toFullAdsDto(Ads entity);
}
