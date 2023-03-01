package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class RegReq {
    private String password;
    private Role role;
    private String username;
}
