package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.User;

import java.util.List;

public interface AdsService {

    public List<AdsDto> findByTitle (String title);
    public List<AdsDto> findAdsByAuthor (User author);
    public List<AdsDto> findAdsByDescriptionContains (String part);


}
