/*package ru.skypro.homework.utils;


import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.AdsImage;
import ru.skypro.homework.entity.User;



@Service
public class MappingUtils {

    private AdsImage adsImage;
    private User user;


    public AdsDto mapToAdsDto(Ads entity) {
        AdsDto adsDto = new AdsDto();
        adsDto.setPk(entity.getPk());
        adsDto.setAuthor(mapToUserDto(entity.getAuthor()));
        adsDto.setPrice(entity.getPrice());
        adsDto.setTitle(entity.getTitle());
        adsDto.setImage(entity.getImage());
        return adsDto;
    }

    public Ads mapToAdsEntity(AdsDto adsDto, User user, AdsImage adsImage) {
        Ads entity = new Ads();
        entity.setPk(adsDto.getPk());
        entity.setAuthor(user);;
        entity.setPrice(adsDto.getPrice());
        entity.setTitle(adsDto.getTitle());
        entity.setImage(adsImage);
        return entity;
    }

    public UserDto mapToUserDto(User userEntity) {
        UserDto userDto = new UserDto();
        userDto.setEmail(userEntity.getEmail());
        userDto.setId(userEntity.getId());
        userDto.setPhone(userEntity.getPhone());
        userDto.setLastName(userEntity.getLastName());
        userDto.setFirstName(userEntity.getFirstName());
        return userDto;
    }

    public User mapToUserEntity(UserDto userDto) {
        User userEntity = new User();
        userEntity.setPhone(userDto.getPhone());
        userEntity.setEmail(userDto.getEmail());;
        userEntity.setId(userDto.getId());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setFirstName(userDto.getFirstName());
        return userEntity;
    }
    public CommentDto mapToCommentDto(AdsComment adsCommentEntity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(adsCommentEntity.getPk());
        commentDto.setAuthor(adsCommentEntity.getAuthor());
        commentDto.setText(adsCommentEntity.getText());
        commentDto.setCreatedAt(adsCommentEntity.getCreatedAt());
        return commentDto;
    }

    public AdsComment mapToAdsCommentEntity(CommentDto commentDto) {
        AdsComment adsCommentEntity = new AdsComment();
        adsCommentEntity.setPk(commentDto.getPk());
        adsCommentEntity.setAuthor(commentDto.getAuthor());;
        adsCommentEntity.setText(commentDto.getText());
        adsCommentEntity.setCreatedAt(commentDto.getCreatedAt());
        return adsCommentEntity;
    }
}*/



