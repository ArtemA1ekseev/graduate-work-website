package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {
UserRepository userRepository;
public UserServiceImpl(UserRepository userRepository){
    this.userRepository=userRepository;
}
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
