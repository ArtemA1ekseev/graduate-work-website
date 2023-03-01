package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Ads {
    @Id
    private int pk;
    private int author;
    private int price;
    private String image;
    private String title;
    private String description;
}
