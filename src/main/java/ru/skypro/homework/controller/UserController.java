package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.AdsImageServiceImpl;

import java.util.Collection;

@EnableMethodSecurity
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "UserController")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;

    private final UserMapper mapper;

    @Operation(summary = "addUser", description = "addUser")
    @PostMapping
    public ResponseEntity<CreateUserDto> addUser(@RequestBody CreateUserDto createUserDto) {
        logger.info("Request for add user");
        User user = userService.createUser(mapper.createUserDtoToEntity(createUserDto));
        return ResponseEntity.ok(mapper.toCreateUserDto(user));
    }

    @Operation(summary = "getUsers", description = "getUsers")
    @GetMapping("/me")
    public ResponseWrapper<UserDto> getUsers() {
        logger.info("Request for get users");
        Collection<User> users = userService.getUsers();
        return ResponseWrapper.of(mapper.toDto(users));
    }

    @Operation(summary = "updateUser", description = "updateUser")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        logger.info("Request for update user");
        User user = mapper.toEntity(userDto);
        return ResponseEntity.ok(mapper.toDto(userService.update(user)));
    }

    @Operation(summary = "setPassword", description = "setPassword")
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPasswordDto) {
        logger.info("Request for create new password");
        if (userService.newPassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword())) {
            return ResponseEntity.ok(newPasswordDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "getUser", description = "getUser")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        logger.info("Request for get user by id");
        User user = userService.getUserById(id);
        return ResponseEntity.ok(mapper.toDto(user));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}/updateRole")
    public ResponseEntity<UserDto> updateRoleUser(@PathVariable long id, Role role) {
        logger.info("Request for update user role");

        UserDto userDto = mapper.toDto(userService.updateRoleUser(id, role));

        return ResponseEntity.ok(userDto);
    }
}