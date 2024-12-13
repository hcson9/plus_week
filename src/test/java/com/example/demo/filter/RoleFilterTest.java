package com.example.demo.filter;/*
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
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;

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
class RoleFilterTest {
  private RoleFilter roleFilter;

  @Mock
  private Authentication authentication;

  private MockHttpServletRequest request = new MockHttpServletRequest();
  private MockHttpServletResponse response = new MockHttpServletResponse();
  private FilterChain filterChain = new MockFilterChain();
  private MockHttpSession session = new MockHttpSession();

  @BeforeEach
  void setUp() {
    request.setSession(session);
  }

  @Test
  void userRoleSuccess() {
    given(authentication.getRole()).willReturn(Role.USER);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);
    RoleFilter filter = new RoleFilter(Role.USER);

    assertDoesNotThrow(() -> filter.doFilter(request, response, filterChain));
  }

  @Test
  void userRoleFailure() {
    given(authentication.getRole()).willReturn(Role.ADMIN);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);
    RoleFilter filter = new RoleFilter(Role.USER);

    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> filter.doFilter(request, response, filterChain));
    assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(exception.getReason()).isEqualTo("user 권한이 필요합니다.");
  }

  @Test
  void noSessionUserFailure() {
    request.setSession(null);
    RoleFilter filter = new RoleFilter(Role.USER);

    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> filter.doFilter(request, response, filterChain));
    assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(exception.getReason()).isEqualTo("로그인 필요");
  }

  @Test
  void adminRoleSuccess() {
    given(authentication.getRole()).willReturn(Role.ADMIN);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);
    RoleFilter filter = new RoleFilter(Role.ADMIN);

    assertDoesNotThrow(() -> filter.doFilter(request, response, filterChain));
  }

  @Test
  void adminRoleFailure() {
    given(authentication.getRole()).willReturn(Role.USER);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);
    RoleFilter filter = new RoleFilter(Role.ADMIN);

    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> filter.doFilter(request, response, filterChain));
    assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(exception.getReason()).isEqualTo("admin 권한이 필요합니다.");
  }

  @Test
  void noSessionAdminFailure() {
    request.setSession(null);
    RoleFilter filter = new RoleFilter(Role.ADMIN);

    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> filter.doFilter(request, response, filterChain));
    assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(exception.getReason()).isEqualTo("로그인 필요");
  }
}