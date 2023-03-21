package ru.skypro.homework.service;

import ru.skypro.homework.dto.CreateUserDto;
import ru.skypro.homework.dto.UserDto;
import java.util.List;

/**
 * Сервис для работы с пользователем
 */
public interface UserService {

    /**
     * Создание пользователя
     * @param createUserDto Объект пользователя для передачи данных
     * @return User Созданный пользователь
     */
    UserDto createUser(CreateUserDto createUserDto);

    /**
     * Получение всех существующих пользователей
     *
     * @return Collection<User>
     */
    List<UserDto> getUsers();

    /**
     * Изменение пользователя
     *
     * @param user Объект пользователя с новыми данными
     * @return User Изменённый пользователь
     */
    UserDto update(UserDto user);

    /**
     * Получение пользователя по ID
     *
     * @param id ID пользователя
     * @return User с данным ID
     */
    UserDto getUserById(long id);

    /**
     * Изменение пароля пользователя
     *
     * @param newPassword новый пароль
     * @param currentPassword старый пароль
     * @return Возвращает true если пароль успешно изменен, иначе false
     */
    boolean newPassword(String newPassword, String currentPassword);
}