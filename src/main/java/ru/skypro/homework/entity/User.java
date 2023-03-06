package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime regDate;
    private String city;
    private String image;
    @OneToMany
    private Collection<Ads> adsCollection;
}