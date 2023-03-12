package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdsCommentDto {

    private int id;

    private int author;

    private String createdAt;

    private String text;
}
