package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdsCommentDto {

    private int pk;

    private int author;

    private String createdAt;

    @NotBlank
    @Size(min = 8)
    private String text;
}