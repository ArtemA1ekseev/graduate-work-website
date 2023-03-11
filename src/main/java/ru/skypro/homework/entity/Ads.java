package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

/**
 * Class of Ads (advertisement/объявление)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {

    /** "ID" field */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** "price/цена" field */
    private Integer price;
    /** "title/заголовок" field */
    private String title;
    /** "description/описание" field */
    private String description;
    /** "image/изображение" field */
    private String image;
    /** "users/пользователь" field */
    @ManyToOne
    private Users users;
    /** "adsCommentList/список комментариев" field
     * @see AdsComment
     * */
    @OneToMany(mappedBy = "ads")
    private List<AdsComment> adsCommentList;

}