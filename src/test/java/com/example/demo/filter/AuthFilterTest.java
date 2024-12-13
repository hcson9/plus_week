package com.example.demo.filter;/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

import com.example.demo.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import java.io.IOException;

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
class AuthFilterTest {
  private AuthFilter authFilter = new AuthFilter();

  private MockHttpServletRequest request = new MockHttpServletRequest();
  private MockHttpServletResponse response = new MockHttpServletResponse();
  private FilterChain filterChain = new MockFilterChain();
  private MockHttpSession session = new MockHttpSession();


  @Test
  void success() throws ServletException, IOException {
    request.setSession(session);
    assertDoesNotThrow(() -> authFilter.doFilter(request, response, filterChain));
  }

  @Test
  void failure() throws ServletException, IOException {

    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> authFilter.doFilter(request, response, filterChain));
    assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(exception.getReason()).isEqualTo("로그인 필요");
  }
}