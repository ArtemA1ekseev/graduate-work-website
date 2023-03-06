package ru.skypro.homework.service;

import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    public User updateUser(User user);
}