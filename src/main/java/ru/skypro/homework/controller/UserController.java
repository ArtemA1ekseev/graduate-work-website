package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    Logger logger = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;

    @Operation(summary = "Создание пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Созданный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            },
            tags = "Users"
    )
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody CreateUserDto createUserDto) {
        logger.info("Request for add user");
        return ResponseEntity.ok(userService.createUser(createUserDto));
    }

    @Operation(summary = "Просмотр всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователи",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            },
            tags = "Users"
    )
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserMe(Authentication authentication) {
        logger.info("Request for get users");
        return ResponseEntity.ok(userService.getUserMe(authentication));
    }

    @Operation(summary = "Изменение информации о пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененная информация",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            },
            tags = "Users"
    )
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Request for update user");
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @Operation(summary = "Изменение пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый пароль",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NewPasswordDto.class)
                            )
                    )
            },
            tags = "Users"
    )
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        logger.info("Request for create new password");
        userService.newPassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword());
        return ResponseEntity.ok(newPasswordDto);
    }

    @Operation(summary = "Поиск пользователя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            },
            tags = "Users"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        logger.info("Request for get user by id");
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Изменение роли пользователя User/Admin",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененные данные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Role.class)
                            )
                    )
            },
            tags = "Users"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}/updateRole")
    public ResponseEntity<UserDto> updateRole(@PathVariable("id") long id, Role role) {
        logger.info("Request for update user role");
        return ResponseEntity.ok(userService.updateRole(id, role));
    }
}