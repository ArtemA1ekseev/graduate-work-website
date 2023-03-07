package ru.skypro.homework.utils;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;

@Service
public class MappingUtils {

    public UserDto mapToUserDto(User entity) {
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        return dto;
    }

    public User mapToUserEntity(UserDto dto) {
        User entity = new User();
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(entity.getLastName());
        return entity;
    }

    public AdsDto mapToAdsDto(Ads entity){
        AdsDto dto = new AdsDto();
        dto.setPk(entity.getPk());
        dto.setAuthor(mapToUserDto(entity.getAuthor()));
        dto.setPrice(entity.getPrice());
        dto.setTitle(entity.getTitle());
        dto.setImage(entity.getImage());
        return dto;
    }

    public Ads mapToAdsEntity(AdsDto dto) {
        Ads entity = new Ads();
        entity.setPk(entity.getPk());
        entity.setAuthor(entity.getAuthor());
        entity.setPrice(entity.getPrice());
        entity.setTitle(entity.getTitle());
        entity.setImage(entity.getImage());
        return entity;
    }
}



