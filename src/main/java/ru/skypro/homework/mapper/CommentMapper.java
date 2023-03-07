package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.AdsComment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.id", target = "author")
    CommentDto commentEntityToAdsCommentDto(AdsComment adsComment);

    @Mapping(source = "pk", target = "id")
    default AdsComment adsCommentDtoToCommentEntity(CommentDto CommentDto);

    List<CommentDto> commentEntitiesToAdsCommentDtos(List<AdsComment> adsCommentList);
}