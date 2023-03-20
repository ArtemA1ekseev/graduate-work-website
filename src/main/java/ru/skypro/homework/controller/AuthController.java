package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginReqDto;
import ru.skypro.homework.dto.RegisterReqDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.impl.AdsImageServiceImpl;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "AuthController")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);


    private final AuthService authService;

    private final UserMapper userMapper;

    @Operation(summary = "Login", description = "Login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto req) {
        logger.info("Request for authorization user");
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Register", description = "Register")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReqDto req) {
        logger.info("Request for registration user");
        if (authService.register(userMapper.toEntity(req))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}