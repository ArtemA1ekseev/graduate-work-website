package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.CreateUserDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserDetailsServiceImpl;
import ru.skypro.homework.service.UserService;

import javax.validation.ValidationException;
import java.util.List;

import static ru.skypro.homework.dto.Role.USER;

/**
 * Имплеменация сервиса для работы с пользователем
 */
@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Override
    public UserDto createUser(CreateUserDto user) {
        logger.info("Was invoked method for create user");
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("user already exists");
            throw new ValidationException(String.format("Пользователь \"%s\" уже существует!", user.getEmail()));
        }
        User createdUser = userMapper.createUserDtoToEntity(user);
        if (createdUser.getRole() == null) {
            createdUser.setRole(USER);
        }
        logger.info("user created");
        return userMapper.toDto(userRepository.save(createdUser));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsers() {
        logger.info("Was invoked method for get users");
        return userMapper.toDto(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserMe(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(UserDto updatedUserDto) {
        logger.info("Was invoked method for update user");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        user.setFirstName(updatedUserDto.getFirstName());
        user.setLastName(updatedUserDto.getLastName());
        user.setPhone(updatedUserDto.getPhone());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(long id) {
        logger.info("Was invoked method for get user by id");
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден!")));
    }

    @Override
    public void newPassword(String newPassword, String currentPassword) {
        logger.info("Was invoked method for create new password");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            logger.info("password updated");
            userDetailsService.loadUserByUsername(user.getEmail());
        }
    }

    @Override
    public UserDto updateRole(long id, Role role) {
        logger.info("Was invoked method for update user role");
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден!"));
        user.setRole(role);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}