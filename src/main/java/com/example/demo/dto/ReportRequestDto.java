package com.example.demo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReportRequestDto {
    private List<Long> userIds;

    public ReportRequestDto(List<Long> userIds) {
        this.userIds = userIds;
    }
}
