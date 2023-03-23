package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;

/**
 * Interface of ads mapper
 */
@Mapper
public interface AdsMapper extends WebMapper<AdsDto, Ads> {

    @Override
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "image", ignore = true)
    @Mapping(source = "pk", target = "id")
    Ads toEntity(AdsDto dto);

    @Override
    @Mapping(target = "author", source = "author.id")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "image", expression = "java(\"/ads/images/\" + entity.getImage().getId())")
    AdsDto toDto(Ads entity);


    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(source = "pk", target = "id")
    Ads toEntity(CreateAdsDto dto);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "image", expression = "java(\"/ads/images/\" + entity.getImage().getId())")
    FullAdsDto toFullAdsDto(Ads entity);

}