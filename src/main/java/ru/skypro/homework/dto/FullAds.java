package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class FullAds   {

    private String authorFirstName;

    private String authorLastName;

    private String description;

    private String email;

    private List<ImageDto> image;

    private String phone;

    private Integer pk;

    private Integer price;
    
    private String title;
}
