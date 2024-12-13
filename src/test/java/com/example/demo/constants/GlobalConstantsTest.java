package com.example.demo.constants;/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * create on 2024. 12. 13..
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
class GlobalConstantsTest {

  @Test
  void testUserAuthConstant() {
    GlobalConstants globalConstants = new GlobalConstants() {
      @Override
      public int hashCode() {
        return super.hashCode();
      }
    };
    // USER_AUTH 상수가 올바르게 설정되었는지 확인
    assertEquals("USER_AUTH", GlobalConstants.USER_AUTH);
  }
}