package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.entity.AdsComment;

@Mapper
public interface AdsCommentMapper extends WebMapper<AdsCommentDto, AdsComment> {

    @Override
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
 // @Mapping(target = "id", source = "pk")
    AdsComment toEntity(AdsCommentDto dto);

    @Override
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "createdAt", source = "entity.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AdsCommentDto toDto(AdsComment entity);
}