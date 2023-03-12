package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdsDto {

    private int id;

    private int author;

    private String image;

    private int price;

    private String title;

    private String description;
}