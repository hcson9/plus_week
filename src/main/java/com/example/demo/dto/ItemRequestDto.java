package com.example.demo.dto;

import lombok.Getter;

@Getter
public class ItemRequestDto {
    private String name;

    private String description;

    private Long managerId;

    private Long ownerId;

    public ItemRequestDto(String name, String description, Long managerId, Long ownerId) {
        this.name = name;
        this.description = description;
        this.managerId = managerId;
        this.ownerId = ownerId;
    }
}
