package ru.skypro.homework.mapper;

import ru.skypro.homework.dto.ImagesDto;
import ru.skypro.homework.entity.Images;

public interface ImagesMapper extends WebMapper<ImagesDto, Images>{

    @Override
    Images toEntity(ImagesDto dto);

    @Override
    ImagesDto toDto(Images entity);
}
