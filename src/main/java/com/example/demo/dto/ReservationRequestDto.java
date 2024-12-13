package com.example.demo.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> 예약 생성 요청. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 10
 */
@Getter
public class ReservationRequestDto {

    /***
     * item id.
     */
    private Long itemId;

    /**
     * user Id.
     */
    private Long userId;

    /**
     * 시작 날짜.
     */
    private LocalDateTime startAt;

    /**
     * 종료 날짜.
     */
    private LocalDateTime endAt;

    /**
     * 생성자.
     *
     * @param itemId itemId.
     * @param userId user Id.
     * @param startAt 시작 날짜
     * @param endAt 종료날짜
     */
    public ReservationRequestDto(Long itemId, Long userId, LocalDateTime startAt, LocalDateTime endAt) {
        this.itemId = itemId;
        this.userId = userId;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
