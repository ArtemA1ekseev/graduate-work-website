package ru.skypro.homework.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}