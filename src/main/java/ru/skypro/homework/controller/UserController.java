package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.UserService;

import javax.validation.Valid;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "UserController")
public class UserController {

    private final UserService userService;

    @Operation(summary = "addUser", description = "addUser")
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(userService.createUser(createUserDto));
    }

    @Operation(summary = "getUsers", description = "getUsers")
    @GetMapping("/me")
    public ResponseWrapper<UserDto> getUsers() {
        return ResponseWrapper.of(userService.getUsers());
    }

    @Operation(summary = "updateUser", description = "updateUser")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @Operation(summary = "setPassword", description = "setPassword")
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        userService.newPassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword());
        return ResponseEntity.ok(newPasswordDto);
    }

    @Operation(summary = "getUser", description = "getUser")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "updateRole", description = "updateRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}/updateRole")
    public ResponseEntity<UserDto> updateRole(@PathVariable("id") long id, Role role) {
        return ResponseEntity.ok(userService.updateRole(id, role));
    }
}