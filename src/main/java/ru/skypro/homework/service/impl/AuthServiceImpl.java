package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserDetailsServiceImpl;
import ru.skypro.homework.service.AuthService;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);


    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public boolean login(String username, String password) {
        logger.info("Was invoked method for user authorization");
        if (!userRepository.existsByEmail(username)) {
            logger.warn("user not found");
            return false;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return passwordEncoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(User user) {
        logger.info("Was invoked method for user registration");
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("user already exists");
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("user registered");
        return true;
    }
}