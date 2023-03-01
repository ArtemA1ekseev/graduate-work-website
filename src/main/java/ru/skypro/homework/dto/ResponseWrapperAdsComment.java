package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAdsComment {
    private int count;
    private List<AdsCommentDto> results;
}
