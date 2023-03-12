package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.ImageDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsImage;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(source = "mediaType", target = "image")
    ImageDto ImageToImageDto(AdsImage adsImage);

    List<ImageDto> ImagesToImagesDto(List<AdsImage> adsImages);
}
