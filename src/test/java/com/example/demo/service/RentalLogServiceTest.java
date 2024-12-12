package com.example.demo.service;/*
 * Created by Hochan Son on 2024. 12. 12.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 12.
 */

import com.example.demo.entity.RentalLog;
import com.example.demo.repository.RentalLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
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
class RentalLogServiceTest {

  @InjectMocks
  private RentalLogService rentalLogService;

  @Mock
  private RentalLogRepository rentalLogRepository;

  @Test
  void saveSuccessTest() {
    RentalLog rentalLog = Mockito.mock(RentalLog.class);
    assertDoesNotThrow(() -> rentalLogService.save(rentalLog));
  }

  @Test
  void saveFailTest() {
    assertThrows(RuntimeException.class, () -> rentalLogService.save(null));
  }
}