package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

import javax.validation.ValidationException;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    @Override
    public void login(String username, String password) {
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Неверно указан пароль!");
        }
    }

    @Override
    public void register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException(String.format("Пользователь \"%s\" уже зарегистрирован!", user.getEmail()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }
}