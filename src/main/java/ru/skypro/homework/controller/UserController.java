package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.UserService;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {
 UserService userService;
    @PatchMapping("/me")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PatchMapping("/me/image")
    public ResponseEntity<UserDto> updateUserImage(@RequestBody UserDto userDto) {
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
}
