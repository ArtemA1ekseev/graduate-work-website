package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginReqDto;
import ru.skypro.homework.dto.RegisterReqDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.AuthService;

import javax.validation.Valid;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@Tag(name = "Авторизация", description = "AuthController")
public class AuthController {

    private final AuthService authService;

    private final UserMapper userMapper;

    @Operation(summary = "Login", description = "Login")
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginReqDto req) {
        authService.login(req.getUsername(), req.getPassword());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Register", description = "Register")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterReqDto req) {
        authService.register(userMapper.toEntity(req));

        return ResponseEntity.ok().build();
    }
}