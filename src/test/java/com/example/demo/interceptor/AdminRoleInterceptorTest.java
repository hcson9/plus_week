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
import com.example.demo.entity.Role;
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
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
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
class AdminRoleInterceptorTest {
  private AdminRoleInterceptor interceptor = new AdminRoleInterceptor();

  @Mock
  private Authentication authentication;

  private MockHttpServletRequest request;
  private MockHttpServletResponse response;
  private MockHttpSession session;

  @BeforeEach
  void setUp() {
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    session = new MockHttpSession();
    request.setSession(session);
  }

  @Test
  void successTest() {
    given(authentication.getRole()).willReturn(Role.ADMIN);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    // then
    boolean result = interceptor.preHandle(request, response, null);
    assertTrue(result);
  }

  @Test
  void checkUserRoleTest() {
    // given
    given(authentication.getRole()).willReturn(Role.USER);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    // then
    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () ->
            interceptor.preHandle(request, response, new Object()));

    assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    assertEquals("ADMIN 권한이 필요합니다.", exception.getReason());
  }

  @Test
  void sessionCheckTest() {
    // given
    request.setSession(null);

    // then
    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () ->
            interceptor.preHandle(request, response, new Object()));

    assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    assertEquals("세션이 끊어졌습니다.", exception.getReason());
  }

}