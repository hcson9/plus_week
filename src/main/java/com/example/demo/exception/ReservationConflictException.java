package com.example.demo.exception;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> 예약관련 Exception. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
public class ReservationConflictException extends RuntimeException {

    /**
     * 생성자.
     *
     * @param message message
     */
    public ReservationConflictException(String message) {
        super(message);
    }
}
