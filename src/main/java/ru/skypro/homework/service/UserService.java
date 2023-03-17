package ru.skypro.homework.service;

import ru.skypro.homework.entity.User;
import java.util.Collection;

/**
 * Сервис для работы с пользователем
 */
public interface UserService {

    /**
     * Создание пользователя
     * @param user Объект пользователя
     * @return User Созданный пользователь
     */
    User createUser(User user);

    /**
     * Получение всех существующих пользователей
     *
     * @return Collection<User>
     */
    Collection<User> getUsers();

    /**
     * Изменение пользователя
     *
     * @param user Объект пользователя с новыми данными
     * @return User Изменённый пользователь
     */
    User update(User user);

    /**
     * Получение пользователя по ID
     * @param id ID пользователя
     * @return User с данным ID
     */
    User getUserById(long id);

    /**
     * Изменение пароля пользователя
     * @param newPassword новый пароль
     * @param currentPassword старый пароль
     * @return Возвращает true если пароль успешно изменен, иначе false
     */
    boolean newPassword(String newPassword, String currentPassword);
}