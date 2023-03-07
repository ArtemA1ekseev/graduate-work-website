package ru.skypro.homework.service;

import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.UserDto;

public interface UserService {

    ResponseWrapperUser getAllUsers();

    UserDto updateUser(UserDto userDto);

    UserDto getUser(Integer id);

}