package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UserDto {

    private String email;

    private String firstName;

    private long id;

    private String lastName;

    private String phone;
}
