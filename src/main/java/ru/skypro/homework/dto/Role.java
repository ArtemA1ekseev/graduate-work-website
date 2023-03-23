package ru.skypro.homework.dto;

import org.springframework.security.core.GrantedAuthority;

/**
 * "
 * Enum Role (тип пользователя) (USER, ADMIN)
 */
public enum Role implements GrantedAuthority {

    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
