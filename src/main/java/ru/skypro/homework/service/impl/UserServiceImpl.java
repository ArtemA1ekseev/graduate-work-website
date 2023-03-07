package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseWrapperUser getAllUsers() {
        List<UserDto> userDtoDtoList = userMapper.usersEntitiesToUserDtos(userRepository.findAllUsers());
        ResponseWrapperUser responseWrapperUser = new ResponseWrapperUser();
        responseWrapperUser.setCount(userDtoDtoList.size());
        responseWrapperUser.setResults(userDtoDtoList);
        return responseWrapperUser;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(UserNotFoundException::new);
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setUsername(userDto.getEmail());
        userRepository.save(user);
        return userDto;
    }

    @Override
    public UserDto getUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.usersEntityToUserDto(user);
    }
}
