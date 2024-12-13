package com.example.demo.service;/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.entity.Item;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.ReservationStatus;
import com.example.demo.entity.User;
import com.example.demo.exception.ReservationConflictException;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
class ReservationServiceTest {

  @InjectMocks
  private ReservationService reservationService;

  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private UserRepository userRepository;

  private List<Reservation> reservations = new ArrayList<>();
  private Reservation reservation = Mockito.mock(Reservation.class);


  void setReservations() {
    User user = Mockito.mock(User.class);
    Item item = Mockito.mock(Item.class);
    given(reservation.getId()).willReturn(1L);
    given(reservation.getUser()).willReturn(user);
    given(reservation.getItem()).willReturn(item);
    given(reservation.getStartAt()).willReturn(LocalDateTime.now());
    given(reservation.getEndAt()).willReturn(LocalDateTime.now());
    given(user.getNickname()).willReturn("user");
    given(item.getName()).willReturn("item");
    reservations.add(reservation);
  }

  @Nested
  class CreateReservation {

    @Test
    void createReservationSuccessTest() {
      // given
      Item item = Mockito.mock(Item.class);
      User user = Mockito.mock(User.class);

      // when
      when(reservationRepository.findConflictingReservations(anyLong(), any(), any())).thenReturn(Collections.emptyList());
      when(itemRepository.findByItemId(anyLong())).thenReturn(item);
      when(userRepository.findUserById(anyLong())).thenReturn(user);

      // then
      assertDoesNotThrow(() ->
              reservationService.createReservation(1L, 2L, LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    void createReservationValidate() {
      // given
      reservations.add(Mockito.mock(Reservation.class));

      // when
      when(reservationRepository.findConflictingReservations(anyLong(), any(), any())).thenReturn(reservations);

      // then
      ReservationConflictException exception =assertThrows(ReservationConflictException.class, () -> reservationService.createReservation(1L, 2L, LocalDateTime.now(), LocalDateTime.now()));
      assertThat(exception.getMessage()).isEqualTo("해당 물건은 이미 그 시간에 예약이 있습니다.");
    }
  }


  @Nested
  class GetReservations {
    @Test
    void getReservationsSuccessTest() {
      // when
      setReservations();
      when(reservationRepository.findAllWithUserAndItem()).thenReturn(reservations);

      // then
      List<ReservationResponseDto> result = reservationService.getReservations();

      assertThat(result).isNotEmpty();
      assertThat(result.get(0).getId()).isEqualTo(reservation.getId());
    }

    @Test
    void getReservationsFailure() {
      // when
      when(reservationRepository.findAllWithUserAndItem()).thenReturn(Collections.emptyList());

      // then
      List<ReservationResponseDto> result = reservationService.getReservations();
      assertThat(result).isEmpty();
    }
  }

  @Nested
  class SearchAndConvertReservations {
    @Test
    void searchAndConvertReservationsSuccess() {
      setReservations();
      when(reservationRepository.findReservations(anyLong(), anyLong())).thenReturn(reservations);

      List<ReservationResponseDto> result = reservationService.searchAndConvertReservations(1L, 2L);

      assertThat(result).isNotEmpty();
      assertThat(result.get(0).getId()).isEqualTo(reservation.getId());
    }

    @Test
    void searchAndConvertReservationsFailure() {
      // when
      when(reservationRepository.findReservations(anyLong(), anyLong())).thenReturn(Collections.emptyList());

      // then
      List<ReservationResponseDto> result = reservationService.searchAndConvertReservations(1L, 2L);
      assertThat(result).isEmpty();
    }
  }


  @Nested
  class UpdateReservationStatus {
    @Test
    void updateReservationStatusSuccessTest() {
      // given
      Reservation reservation = Mockito.mock(Reservation.class);

      // when
      when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

      // then
      assertDoesNotThrow(() -> reservationService.updateReservationStatus(1L, "EXPIRED"));
    }

    @Test
    void updateReservationStatusFailTest() {
      // when
      when(reservationRepository.findById(anyLong())).thenReturn(Optional.empty());

      // then
      IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
              () -> reservationService.updateReservationStatus(1L, "EXPIRED"));
      assertThat(exception.getMessage()).isEqualTo("해당 ID에 맞는 데이터가 존재하지 않습니다.");
    }
  }
}