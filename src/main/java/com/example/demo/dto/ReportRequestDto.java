package com.example.demo.dto;

import lombok.Getter;

import java.util.List;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> 리포트 관련 Dto. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Getter
public class ReportRequestDto {

    /**
     * 조회할 ids.
     */
    private List<Long> userIds;

    /**
     * 생성자.
     *
     * @param userIds 조회할 ids
     */
    public ReportRequestDto(List<Long> userIds) {
        this.userIds = userIds;
    }
}
