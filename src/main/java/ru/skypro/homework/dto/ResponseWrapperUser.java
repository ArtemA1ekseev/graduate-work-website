package ru.skypro.homework.dto;

import lombok.Data;
import java.util.List;

@Data
public class ResponseWrapperUser {

    private Integer count;

    private List<UserDto> results;
}
