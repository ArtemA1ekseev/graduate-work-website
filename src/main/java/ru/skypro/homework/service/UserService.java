package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CreateUserDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;

import java.util.List;

/**
 * Интерфейс сервиса для работы с пользователем
 */
public interface UserService {

    /**
     * Создание пользователя
     *
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

    UserDto getUserMe(Authentication authentication);

    /**
     * Изменение пользователя
     *
     * @param user Объект пользователя с новыми данными
     * @return User Изменённый пользователь
     */
    UserDto updateUser(UserDto user);

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
     * @param newPassword     новый пароль
     * @param currentPassword старый пароль
     * @return Возвращает true если пароль успешно изменен, иначе false
     */
    void newPassword(String newPassword, String currentPassword);


    /**
     * Изменение роли пользователя
     *
     * @param id   идентификатор пользователя
     * @param role новая роль
     */
    UserDto updateRole(long id, Role role);
}