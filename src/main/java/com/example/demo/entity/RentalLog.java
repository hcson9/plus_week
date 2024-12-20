package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> 대여 이력. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalLog {

    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * message.
     */
    private String logMessage;

    /**
     * 로그 타입.
     * <pre>
     *   - SUCCESS
     *   - FAILURE
     * </pre>
     */
    private String logType; // SUCCESS, FAILURE

    /**
     * 예약 id.
     */
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    /**
     * 생성자.
     *
     * @param reservation 예약.
     * @param logMessage 로그메시지
     * @param logType 로그타입.
     */
    public RentalLog(Reservation reservation, String logMessage, String logType) {
        this.reservation = reservation;
        this.logMessage = logMessage;
        this.logType = logType;
    }

}
