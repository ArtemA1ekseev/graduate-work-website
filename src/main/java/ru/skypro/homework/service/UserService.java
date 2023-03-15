package ru.skypro.homework.service;

import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;

import java.util.Collection;

public interface UserService {

    User createUser(User user);

    Collection<User> getUsers();

    User update(User user);

    User getUserById(long id);

    boolean newPassword(String newPassword, String currentPassword);

    User updateRoleUser(long id, Role role);
}