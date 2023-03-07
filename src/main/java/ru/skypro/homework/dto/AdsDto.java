package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.entity.AdsImage;

@Data
public class AdsDto {
    private UserDto author;
    private AdsImage image;
    private int pk;
    private int price;
    private String title;
}
