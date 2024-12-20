package com.example.demo.dto;

import com.example.demo.entity.Role;
import lombok.Getter;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> Authentication. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Getter
public class Authentication {

    /**
     * id.
     */
    private final Long id;

    /**
     * Role.
     */
    private final Role role;

    /**
     * 생성자.
     *
     * @param id id
     * @param role Role
     */
    public Authentication(Long id, Role role) {
        this.id = id;
        this.role = role;
    }
}
