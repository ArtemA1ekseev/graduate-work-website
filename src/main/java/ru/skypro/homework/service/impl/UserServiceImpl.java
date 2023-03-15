package ru.skypro.homework.service.impl;


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
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        User createUser = userRepository.findById(user.getId()).orElse(user);
        if (createUser.getRole() == null) {
            createUser.setRole(USER.name());
        }
        return userRepository.save(createUser);
    }

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        User initialUser = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
//        User initialUser = userRepository.findById(user.getId()).orElse(user);
        user.setId(initialUser.getId());
        user.setEmail(initialUser.getEmail());
        user.setPassword(initialUser.getPassword());
        user.setRole(initialUser.getRole());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден!"));
    }

    @Override
    public boolean newPassword(String newPassword, String currentPassword) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        if(passwordEncoder.matches(currentPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            userDetailsService.loadUserByUsername(user.getEmail());
            return true;
        }

        return false;
    }

    @Override
    public User updateRoleUser(long id, Role role) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден!"));

        user.setRole(role.name());

        userRepository.save(user);

        userDetailsService.loadUserByUsername(user.getEmail());

        return user;
    }
}