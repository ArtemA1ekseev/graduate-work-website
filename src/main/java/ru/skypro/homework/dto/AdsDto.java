package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdsDto {

    private int pk;

    private int author;

    private String image;

    private int price;

    @NotBlank
    @Size(min = 8)
    private String title;

    @NotBlank
    @Size(min = 8)
    private String description;
}