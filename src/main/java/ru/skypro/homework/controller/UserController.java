package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "UserController")
public class UserController {

    private final UserService userService;

    private final UserMapper mapper;

    @Operation(summary = "addUser", description = "addUser")
    @PostMapping
    public ResponseEntity<CreateUserDto> addUser(@Valid @RequestBody CreateUserDto createUserDto) {

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
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {

        User user = mapper.toEntity(userDto);

        return ResponseEntity.ok(mapper.toDto(userService.updateUser(user)));
    }

    @Operation(summary = "setPassword", description = "setPassword")
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        userService.newPassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword());

        return ResponseEntity.ok(newPasswordDto);
    }

    @Operation(summary = "getUser", description = "getUser")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(mapper.toDto(user));
    }

    @Operation(summary = "updateRole", description = "updateRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}/updateRole")
    public ResponseEntity<UserDto> updateRole(@PathVariable("id") long id, Role role) {

        UserDto userDto = mapper.toDto(userService.updateRole(id, role));

        return ResponseEntity.ok(userDto);
    }
}