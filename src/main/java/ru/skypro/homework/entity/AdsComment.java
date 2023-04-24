package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class of AdsComment (advertisement comment/комментарий в объявлениях).
 */
@Entity
@Table(name = "ads_comment")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class AdsComment {
    /**
     * "ID/ id комментария" field
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * "users/пользователь" field
     */
    @ManyToOne
    private User author;
    /**
     * "createdAt/время создания комментария" field
     */
    private LocalDateTime createdAt;
    /**
     * "text/текст комментария" field
     */
    private String text;
    /**
     * "ads/объявление" field
     */
    @ManyToOne
    private Ads ads;
}