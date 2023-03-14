package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdsDto {

    private int pk;

    private int author;

    private String image;

    private int price;

    private String title;

    private String description;
}