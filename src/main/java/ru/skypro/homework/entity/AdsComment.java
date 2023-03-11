package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Class of AdsComment (advertisement comment/комментарий в объявлениях)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsComment {

    /** "ID/ id комментария" field */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** "createdAt/время создания комментария" field */
    private OffsetDateTime createdAt;
    /** "text/текст" field */
    private String text;
    /** "users/пользователь" field */
    @ManyToOne
    private Users users;
    /** "ads/объявление" field */
    @ManyToOne
    private Ads ads;
}
