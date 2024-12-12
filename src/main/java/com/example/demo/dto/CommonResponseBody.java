package com.example.demo.dto;

import lombok.Getter;

@Getter
public class CommonResponseBody<T> {
  private final String message;
  private final T data;

  public CommonResponseBody(String message, T data) {
    this.message = message;
    this.data = data;
  }

  public CommonResponseBody(String message) {
    this(message, null);
  }
}
