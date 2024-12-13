package com.example.demo.dto;

import lombok.Getter;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> Item 생성 Request dto. </p>
 *
 * @author Hochan Son
 * @version 1.0
 */
@Getter
public class ItemRequestDto {

    /**
     * 이름.
     */
    private String name;

    /**
     * 설명.
     */
    private String description;

    /**
     * 매니저 Id.
     */
    private Long managerId;

    /**
     * owner Id.
     */
    private Long ownerId;

    /**
     * 생성자.
     *
     * @param name 이름
     * @param description 설명
     * @param managerId 매니저 id
     * @param ownerId owner id
     */
    public ItemRequestDto(String name, String description, Long managerId, Long ownerId) {
        this.name = name;
        this.description = description;
        this.managerId = managerId;
        this.ownerId = ownerId;
    }
}
