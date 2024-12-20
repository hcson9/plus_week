package com.example.demo.util;/*
 * Created by Hochan Son on 2024. 12. 12.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * create on 2024. 12. 12..
 * create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @see
 * @since 지원하는 자바버전 (ex : 5+ 5이상)
 */
class PasswordEncoderTest {

  private PasswordEncoder passwordEncoder;
  private String password = "123456";

  @Test
  void encodeTest() {
    String encoded = passwordEncoder.encode(password);
    assertThat(encoded).isNotEqualTo(password);
  }

  @Test
  void matchesSuccessTest() {
    // given
    String encoded = passwordEncoder.encode(password);

    // then
    assertThat(passwordEncoder.matches(password, encoded)).isTrue();
  }

  @Test
  void matchesFailTest() {
    // given
    String encoded = passwordEncoder.encode(password);

    // then
    assertThat(passwordEncoder.matches("1234567", encoded)).isFalse();
  }

  @Test
  void constructor() {
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    assertNotNull(passwordEncoder);
  }
}