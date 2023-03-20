package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User author;

    private int price;

    private String title;

    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;
}