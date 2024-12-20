package com.example.demo.config;/*
 * Created by Hochan Son on 2024. 12. 20.
 * As part of
 *
 * Copyright (C)  () - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Backend Team <hc.son9@google.com>, 2024. 12. 20.
 */

import com.example.demo.interceptor.AdminRoleInterceptor;
import com.example.demo.interceptor.AuthInterceptor;
import com.example.demo.interceptor.UserRoleInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * create on 2024. 12. 20. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> 클래스 설명. </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @see
 * @since 지원하는 자바버전 (ex : 5+ 5이상)
 */
@ExtendWith(MockitoExtension.class)
class WebConfigTest {

  @Mock
  private AuthInterceptor authInterceptor;

  @Mock
  private UserRoleInterceptor userRoleInterceptor;

  @Mock
  private AdminRoleInterceptor adminRoleInterceptor;

  @Mock
  private InterceptorRegistration registration;

  @InjectMocks
  private WebConfig webConfig;


  @Test
  void addInterceptors() {
    // given
    InterceptorRegistry registry = Mockito.mock(InterceptorRegistry.class);

    // when
    when(registry.addInterceptor(authInterceptor)).thenReturn(registration);
    when(registry.addInterceptor(userRoleInterceptor)).thenReturn(registration);
    when(registry.addInterceptor(adminRoleInterceptor)).thenReturn(registration);
    when(registration.addPathPatterns(any(String[].class))).thenReturn(registration);

    // then
    assertDoesNotThrow(() -> webConfig.addInterceptors(registry));
  }

  @Test
  void authFilterTest() {
    assertDoesNotThrow(() -> webConfig.authFilter());
  }

  @Test
  void userRoleFilterTest() {
    assertDoesNotThrow(() -> webConfig.userRoleFilter());
  }
}