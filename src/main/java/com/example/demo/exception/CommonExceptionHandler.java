/*
 * Created by Hochan Son on 2024. 12. 20.
 * As part of
 *
 * Copyright (C)  () - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Backend Team <hc.son9@google.com>, 2024. 12. 20.
 */

package com.example.demo.exception;

import com.example.demo.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

/**
 * create on 2024. 12. 20. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> ExceptionHandler. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@ControllerAdvice
public class CommonExceptionHandler {

  /**
   * s3 파일 찾지 못하면 발생.
   *
   * @param e {@code NoSuchKeyException}
   * @return {@code ResponseEntity}
   */
  @ExceptionHandler(NoSuchKeyException.class)
  public ResponseEntity<CommonResponseBody<String>> noSuchKeyException(NoSuchKeyException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new CommonResponseBody<>("Not Found File"));
  }

  /**
   * 권한 문제.
   *
   * @param e {@code UnauthorizedException}
   * @return {@code ResponseEntity}
   */
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<CommonResponseBody<String>> unauthorizedException(UnauthorizedException e) {
    return ResponseEntity.status(e.getStatusCode())
            .body(new CommonResponseBody<>(e.getMessage()));
  }

  /**
   * 예약 중복시 발생
   *
   * @param e {@code ReservationConflictException}
   * @return {@code ResponseEntity}
   */
  @ExceptionHandler(ReservationConflictException.class)
  public ResponseEntity<CommonResponseBody<String>> reservationConflictException(ReservationConflictException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new CommonResponseBody<>(e.getMessage()));
  }
}
