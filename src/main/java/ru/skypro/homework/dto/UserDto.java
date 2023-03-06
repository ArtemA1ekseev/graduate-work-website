package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}