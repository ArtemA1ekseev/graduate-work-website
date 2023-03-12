package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdsDto {

    private Integer author;

    private List<String> image;

    private Integer pk;

    private Integer price;
    
    private String title;
}
