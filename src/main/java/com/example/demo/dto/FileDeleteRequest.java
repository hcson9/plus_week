package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * create on 2024/05/13 create by IntelliJ IDEA.
 *
 * <p> New Project. </p>
 *
 * @author HoChan Son (hcson)
 * @version 1.0
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileDeleteRequest {

  /**
   * 파일 경로.
   */
  @NotNull
  private String key;

  /**
   * 생성자.
   *
   * @param key 파일 경로
   */
  public FileDeleteRequest(String key) {
    this.key = key;
  }
}
