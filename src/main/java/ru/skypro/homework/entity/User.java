package ru.skypro.homework.entity;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "users")
public class User {
    @Id
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime regDate;
    private String city;
    private String image;
}