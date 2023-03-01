package ru.skypro.homework.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class Comment {

    private int author;
    private LocalDateTime createdAt;
    private int pk;
    private String text;
}
