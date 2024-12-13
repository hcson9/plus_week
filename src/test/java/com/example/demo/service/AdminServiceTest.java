package com.example.demo.service;/*
 * Created by Hochan Son on 2024. 12. 12.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 12.
 */

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
class AdminServiceTest {

  @InjectMocks
  private AdminService adminService;

  @Mock
  private UserRepository userRepository;

  @Test
  void reportUsersSuccessTest() {
    // given
    List<Long> userIds = List.of(1L, 2L, 3L);
    List<User> users = new ArrayList<>();
    users.add(Mockito.mock(User.class));
    users.add(Mockito.mock(User.class));

    // when
    when(userRepository.findAllById(userIds))
            .thenReturn(users);

    // then
    assertDoesNotThrow(() -> adminService.reportUsers(userIds));
  }

  @Test
  void reportUsersFailureTest() {
    // when
    when(userRepository.findAllById(anyCollection())).thenReturn(Collections.emptyList());

    // then
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> adminService.reportUsers(List.of(1L, 2L, 3L)));

    assertThat(exception.getMessage()).isEqualTo("사용자가 없습니다.");

  }
}