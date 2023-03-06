package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class AdsComment {
    @Id
    private int pk;
    private int author;
    private LocalDateTime createdAt;
    private String text;
}
