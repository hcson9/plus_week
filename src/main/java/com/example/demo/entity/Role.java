package com.example.demo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> Role. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    USER("user"),
    ADMIN("admin");

    /**
     * 이름.
     */
    private final String name;

    /**
     * String to Role.
     *
     * @param roleName 조회할 Role
     * @return Role
     * @throws IllegalArgumentException 없으면 발생
     */
    public static Role of(String roleName) {
        for (Role role : values()) {
            if (role.getName().equals(roleName)) {
                return role;
            }
        }

        throw new IllegalArgumentException("해당하는 이름의 권한을 찾을 수 없습니다: " + roleName);

    }
}
