package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer price;
    private String title;

    private String description;
    @OneToMany
    private List<AdsImage> image;

    @ManyToOne
    private Users users;

    @OneToMany
    private List<AdsComment> adsCommentList;

}