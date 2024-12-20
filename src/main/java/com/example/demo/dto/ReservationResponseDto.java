package com.example.demo.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> 예약 응답. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Getter
public class ReservationResponseDto {

    /**
     * id.
     */
    private Long id;

    /**
     * 별칭.
     */
    private String nickname;

    /**
     * item 명.
     */
    private String itemName;

    /**
     * 시작일.
     */
    private LocalDateTime startAt;

    /**
     * 종료일.
     */
    private LocalDateTime endAt;

    /**
     * 생성자.
     *
     * @param id id
     * @param nickname 별칭
     * @param itemName itemName
     * @param startAt 시작일
     * @param endAt 종료일
     */
    public ReservationResponseDto(Long id, String nickname, String itemName, LocalDateTime startAt, LocalDateTime endAt) {
        this.id = id;
        this.nickname = nickname;
        this.itemName = itemName;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
