package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> 로그인 관련 dto. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Getter
public class LoginRequestDto {

    /**
     * email.
     */
    @NotBlank
    @Email
    private String email;

    /**
     * password.
     */
    @NotBlank
    private String password;

    /**
     * 생성자.
     *
     * @param email email
     * @param password password
     */
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
