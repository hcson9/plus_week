package com.example.demo.interceptor;/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

import com.example.demo.constants.GlobalConstants;
import com.example.demo.dto.Authentication;
import com.example.demo.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

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
@ExtendWith(MockitoExtension.class)
class AuthInterceptorTest {


  private AuthInterceptor authInterceptor = new AuthInterceptor();

  @Mock
  private Authentication authentication;

  private MockHttpServletRequest request = new MockHttpServletRequest();
  private MockHttpServletResponse response = new MockHttpServletResponse();
  private MockHttpSession session = new MockHttpSession();

  @Test
  void success() {
    request.setSession(session);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    boolean result =  assertDoesNotThrow(() -> authInterceptor.preHandle(request, response, null));
    assertTrue(result);
  }

  @Test
  void fail() {
    request.setSession(session);

    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> authInterceptor.preHandle(request, response, null));

    assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(exception.getReason()).isEqualTo("로그인이 필요합니다.");
  }
  @Test

  void sessionCheckTest() {
    // given
    request.setSession(null);

    // then
    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () ->
            authInterceptor.preHandle(request, response, new Object()));

    assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    assertEquals("세션이 끊어졌습니다.", exception.getReason());
  }
}