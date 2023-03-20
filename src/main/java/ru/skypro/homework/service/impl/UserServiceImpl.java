package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserDetailsServiceImpl;
import ru.skypro.homework.service.UserService;

import java.util.Collection;

import static ru.skypro.homework.dto.Role.USER;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    private final UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        logger.info("Was invoked method for create user");
        User createUser = userRepository.findById(user.getId()).orElse(user);
        if (createUser.getRole() == null) {
            createUser.setRole(USER.name());
        }

        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        logger.info("user created");
        return userRepository.save(createUser);
    }

    @Override
    public Collection<User> getUsers() {
        logger.info("Was invoked method for get users");
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        logger.info("Was invoked method for update user");
        User initialUser = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        user.setId(initialUser.getId());
        user.setEmail(initialUser.getEmail());
        user.setPassword(initialUser.getPassword());
        user.setRole(initialUser.getRole());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        logger.info("Was invoked method for get user by id");
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден!"));
    }

    @Override
    public boolean newPassword(String newPassword, String currentPassword) {
        logger.info("Was invoked method for create new password");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            userDetailsService.loadUserByUsername(user.getEmail());
            logger.info("password updated");
            return true;
        }
        logger.warn("password not updated");
        return false;
    }

    @Override
    public User updateRoleUser(long id, Role role) {
        logger.info("Was invoked method for update user role");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден!"));
        user.setRole(role.name());
        userRepository.save(user);
        userDetailsService.loadUserByUsername(user.getEmail());
        return user;
    }
}