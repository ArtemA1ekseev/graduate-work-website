package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateUserDto;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserService;
import java.util.Collection;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "UserController")
public class UserController {

    private final UserService userService;

    private final UserMapper mapper;

    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Operation(summary = "addUser", description = "addUser")
    @PostMapping
    public ResponseEntity<CreateUserDto> addUser(@RequestBody CreateUserDto createUserDto) {

        User user = userService.createUser(mapper.createUserDtoToEntity(createUserDto));

        return ResponseEntity.ok(mapper.toCreateUserDto(user));
    }

    @Operation(summary = "getUsers", description = "getUsers")
    @GetMapping("/me")
    public ResponseWrapper<UserDto> getUsers() {

        Collection<User> users = userService.getUsers();

        return ResponseWrapper.of(mapper.toDto(users));
    }

    @Operation(summary = "updateUser", description = "updateUser")
    @PatchMapping("/me")
    public UserDto update(@RequestBody UserDto userDto) {
        User user = mapper.toEntity(userDto);
        return mapper.toDto(userService.update(user));
    }

//    @Operation(summary = "setPassword", description = "setPassword")
//    @PostMapping("/set_password")
//    public NewPasswordDto setPassword(@RequestBody NewPasswordDto newPasswordDto) {
//
//    }

    @Operation(summary = "getUser", description = "getUser")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(mapper.toDto(user));
    }
}