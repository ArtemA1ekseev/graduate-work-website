package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.skypro.homework.constant.Regexp.EMAIL_REGEXP;

@Data
public class LoginReqDto {

    @Email(regexp = EMAIL_REGEXP)
    @Schema(example = "user@user.ru")
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;
}