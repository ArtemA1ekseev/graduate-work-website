package ru.skypro.homework.constant;

/**
 * Класс констант для проверки номера телефона и почты
 */
public final class Regexp {

    public static final String EMAIL_REGEXP = ".+@.+[.]..+";

    public static final String PHONE_REGEXP = "\\+7\\d{10}";

    private Regexp() {
    }
}