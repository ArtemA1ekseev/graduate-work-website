package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Class of Ads (advertisement/объявление).
 */
@Entity
@Table(name = "ads")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Ads {
    /**
     * "ID/ id обьявления" field
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
     * "price/цена" field
     */
    private int price;
    /**
     * "title/заголовок" field
     */
    private String title;
    /**
     * "description/описание обьявления" field
     */
    private String description;
    /**
     * "image/изображение" field
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;
}