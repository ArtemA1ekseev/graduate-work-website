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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.dto.LoginReqDto;
import ru.skypro.homework.dto.RegisterReqDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.AuthService;

import javax.validation.Valid;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@Tag(name = "Авторизация", description = "AuthController")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);


    private final AuthService authService;

    private final UserMapper userMapper;

    @Operation(summary = "Авторизация пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Авторизированный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LoginReqDto.class)
                            )
                    )
            },
            tags = "Users"
    )
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginReqDto req) {
        logger.info("Request for authorization user");
        authService.login(req.getUsername(), req.getPassword());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Регистрация пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Зарегистрированный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RegisterReqDto.class)
                            )
                    )
            },
            tags = "Users"
    )
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterReqDto req) {
        logger.info("Request for registration user");
        authService.register(userMapper.toEntity(req));

        return ResponseEntity.ok().build();
    }
}