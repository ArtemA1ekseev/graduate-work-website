package ru.skypro.homework.mapStruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(source = "user.role", target = "role")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "user.password", target = "password")
    UserDto modelToDto(User user);

    List<UserDto> modelsToDTOs(List<User> userList);
}
