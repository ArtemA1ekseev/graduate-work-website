package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class ImagesDto {

    private String id;

    private byte[] image;

    private String filePath;

    private long fileSize;
}