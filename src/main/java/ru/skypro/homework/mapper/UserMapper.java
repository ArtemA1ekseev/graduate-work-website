package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.CreateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

@Mapper
public interface UserMapper extends WebMapper<UserDto, User> {

    CreateUserDto toCreateUserDto(User entity);

    User createUserDtoToEntity(CreateUserDto dto);

}