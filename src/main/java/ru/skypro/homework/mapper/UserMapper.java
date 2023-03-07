package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Users;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto usersEntityToUserDto(Users users);

    List<UserDto> usersEntitiesToUserDtos(List<Users> usersList);
}
