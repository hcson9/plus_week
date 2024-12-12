package com.example.demo.service;/*
 * Created by Hochan Son on 2024. 12. 12.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 12.
 */

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.PasswordEncoder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.NotExtensible;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Test
  void signupWithEmailTest() {
    // given
    UserRequestDto requestDto = new UserRequestDto("user", "test@123.com", "안녕하세요", "123456");

    // then
    assertDoesNotThrow(() -> userService.signupWithEmail(requestDto));
  }


  @Nested
  class loginUser{
    @Test
    void loginUserSuccessfullyTest() {
      // given
      LoginRequestDto requestDto = new LoginRequestDto("user", "123456");
      User user = Mockito.mock(User.class);
      given(user.getPassword()).willReturn(PasswordEncoder.encode("123456"));

      // when
      when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(user);

      // then
      assertDoesNotThrow(() -> userService.loginUser(requestDto));
    }

    @Test
    void loginUserEmptyUserTest() {
      // given
      LoginRequestDto requestDto = new LoginRequestDto("user", "123456");

      // when
      when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(null);


      // then
      assertThrows(ResponseStatusException.class, () -> userService.loginUser(requestDto));
    }

    @Test
    void loginUserInvalidPasswordTest() {
      // given
      LoginRequestDto requestDto = new LoginRequestDto("user", "123456");
      User user = Mockito.mock(User.class);
      given(user.getPassword()).willReturn(PasswordEncoder.encode("1234567"));

      // when
      when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(user);

      // then
      assertThrows(ResponseStatusException.class, () -> userService.loginUser(requestDto));
    }
  }

}