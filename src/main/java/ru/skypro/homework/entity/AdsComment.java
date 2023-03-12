package ru.skypro.homework.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "ads_comment")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class AdsComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private long id;

    @ManyToOne
    private User author;

    private String createdAt;

    private String text;
}
