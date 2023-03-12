package ru.skypro.homework.mapper.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.RegisterReqDto;
import ru.skypro.homework.entity.User;

@Mapper
public interface UserMapperToRegReq {

    @Mapping(source = "email", target = "username")
    RegisterReqDto toDto(User entity);

    @Mapping(source = "username", target = "email")
    User toEntity(RegisterReqDto dto);
}