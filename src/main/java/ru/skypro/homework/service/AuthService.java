package ru.skypro.homework.service;

import ru.skypro.homework.entity.User;

/**
 * Сервис для регистрации пользователя и входа
 */
public interface AuthService {

    /**
     * @param username Логин (email)
     * @param password Пароль
     */
    void login(String username, String password);

    /**
     * @param user Объект пользователя
     */
    void register(User user);
}