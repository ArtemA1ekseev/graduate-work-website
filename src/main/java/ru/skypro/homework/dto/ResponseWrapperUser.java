package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperUser {
    private int count;
    private List<User> results;
}
