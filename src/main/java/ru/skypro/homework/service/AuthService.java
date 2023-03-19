package ru.skypro.homework.service;

import ru.skypro.homework.entity.User;

/**
 * Сервис для регистрации пользователя и входа
 */
public interface AuthService {
    /**
     * @param userName Логин(email)
     * @param password Пароль
     * @return Возвращает true если вход выполнен успешно, иначе false.
     */
    boolean login(String userName, String password);
    /**
     * @param user Объект пользователя
     * @return Возвращает true если такого пользователя ещё не существует и регистрация прошла успешно, иначе false.
     */
    boolean register(User user);
}