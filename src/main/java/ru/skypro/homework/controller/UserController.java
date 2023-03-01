package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.UserDto;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public ResponseEntity<CreateUser> addUser(@RequestBody CreateUser createUser) {
        return ResponseEntity.ok(createUser);
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperUser> getUsers() {
        return ResponseEntity.ok(new ResponseWrapperUser());
    }

    @PostMapping("/set_password")
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.ok(newPassword);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
        return ResponseEntity.ok(new UserDto());
    }
}
