package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.Getter;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> 유저 생성 요청. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Getter
public class UserRequestDto {

    /**
     * 이메일.
     */
    private String email;

    /**
     * 별명.
     */
    private String nickname;

    /**
     * password.
     */
    private String password;

    /**
     * role.
     */
    private String role;

    /**
     * 생성자.
     *
     * @param role role
     * @param email email
     * @param nickname 별명
     * @param password password.
     */
    public UserRequestDto(String role, String email, String nickname, String password) {
        this.role = role;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    /**
     * 비밀번호 변경.
     *
     * @param encryptedPassword 변경할 password
     */
    public void updatePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
}