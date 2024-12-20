package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> User Entity. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Entity
@Getter
@Table(name = "`user`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * email.
     */
    private String email;


    /**
     * 별명.
     */
    private String nickname;

    /**
     * 비밀번호.
     */
    private String password;

    /**
     * 상태.
     */
    @Enumerated(EnumType.STRING)
    private Status status; // NORMAL, BLOCKED

    /**
     * Role.
     */
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    /**
     * 생성자.
     *
     * @param role role
     * @param email email
     * @param nickname 별명
     * @param password 비밀번호
     */
    public User(String role, String email, String nickname, String password) {
        this.role = Role.of(role);
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.status = Status.PENDING;
    }

    /**
     * 상태 업데이트.
     *
     * @param status 변경할 상태.
     */
    public void updateStatus(Status status) {
        this.status = status;
    }
}
