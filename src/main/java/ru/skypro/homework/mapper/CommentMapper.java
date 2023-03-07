package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.entity.AdsComment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "users.id", target = "author")
    AdsCommentDto commentEntityToAdsCommentDto(AdsComment adsComment);

    @Mapping(source = "pk", target = "id")
    AdsComment adsCommentDtoToCommentEntity(AdsCommentDto adsCommentDto);

    List<AdsCommentDto> commentEntitiesToAdsCommentDtos(List<AdsComment> adsCommentList);
}