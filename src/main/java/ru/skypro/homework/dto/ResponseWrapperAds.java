package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAds {

    private Integer count;

    private List<AdsDto> results;
}
