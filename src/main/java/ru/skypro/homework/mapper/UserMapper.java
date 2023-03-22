package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CreateUserDto;
import ru.skypro.homework.dto.RegisterReqDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

/**
 * Interface of user mapper
 */
@Mapper
public interface UserMapper extends WebMapper<UserDto, User> {

    CreateUserDto toCreateUserDto(User entity);

    User createUserDtoToEntity(CreateUserDto dto);

    @Mapping(source = "email", target = "username")
    RegisterReqDto toDtoRegReq(User entity);

    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(source = "username", target = "email")
    User toEntity(RegisterReqDto dto);

}