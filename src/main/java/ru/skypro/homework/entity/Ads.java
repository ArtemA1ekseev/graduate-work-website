package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Ads {
    @Id
    @GeneratedValue
    private int pk;
    private int price;
    private String title;
    private String description;
    @ManyToOne
    private User author;
    @OneToOne
    private AdsImage image;

    public Ads() {
    }

//    public int getPk() {
//        return pk;
//    }
//
//    public User getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(User author) {
//        this.author = author;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    public AdsImage getImage() {
//        return image;
//    }
//
//    public void setImage(AdsImage image) {
//        this.image = image;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
}
