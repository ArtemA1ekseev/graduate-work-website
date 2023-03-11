package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.Role;
import javax.persistence.*;
import java.util.List;
/**
 * Class of Users (users/пользователь)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    /** "ID/id пользователя" field */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** "firstName/имя" field */
    private String firstName;
    /** "lastName/фамилия" field */
    private String lastName;
    /** "email" field */
    private String email;
    /** "phone/номер телефона" field */
    private String phone;
    /** "username/имя пользователя" field */
    private String username;
    /** "password/пароль" field */
    private String password;
    /** "role/тип пользователя" (USER, ADMIN) field */
    @Enumerated(EnumType.STRING)
    private Role role;
    /**
     * "adsList/список обьявлений" field
     * @see Ads
     */
    @OneToMany(mappedBy = "users")
    private List<Ads> adsList;
    /**
     * "adsCommentList/список комментариев" field
     * @see AdsComment
     * */
    @OneToMany(mappedBy = "users")
    private List<AdsComment> adsCommentList;
}