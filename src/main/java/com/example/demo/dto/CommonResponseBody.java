package com.example.demo.dto;

import lombok.Getter;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> 공통 응답. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Getter
public class CommonResponseBody<T> {
  /**
   * message.
   */
  private final String message;

  /**
   * 응답 데이터.
   */
  private final T data;

  /**
   * 생성자.
   *
   * @param message message
   * @param data data
   */
  public CommonResponseBody(String message, T data) {
    this.message = message;
    this.data = data;
  }

  /**
   * message 만 있는 생성자.
   *
   * @param message 메시지
   */
  public CommonResponseBody(String message) {
    this(message, null);
  }
}
