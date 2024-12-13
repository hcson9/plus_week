package com.example.demo.entity;/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

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
class ReservationTest {



  @Test
  void updateStatus() {
    //
    Item item = Mockito.mock(Item.class);
    User user = Mockito.mock(User.class);
    Reservation reservation = new Reservation(item, user, LocalDateTime.now(), LocalDateTime.now());

    assertDoesNotThrow(() -> reservation.updateStatus(ReservationStatus.APPROVED));
  }

  @Test
  void updateStatus2() {
    //
    Item item = Mockito.mock(Item.class);
    User user = Mockito.mock(User.class);
    Reservation reservation = new Reservation(item, user, LocalDateTime.now(), LocalDateTime.now());

    assertDoesNotThrow(() -> reservation.updateStatus(ReservationStatus.EXPIRED));
  }

  @Test
  void updateStatus3() {
    //
    Item item = Mockito.mock(Item.class);
    User user = Mockito.mock(User.class);
    Reservation reservation = new Reservation(item, user, LocalDateTime.now(), LocalDateTime.now());

    assertDoesNotThrow(() -> reservation.updateStatus(ReservationStatus.CANCELED));
  }

  @Test
  void updateStatus4() {
    //
    Item item = Mockito.mock(Item.class);
    User user = Mockito.mock(User.class);
    Reservation reservation = new Reservation(item, user, LocalDateTime.now(), LocalDateTime.now());

    reservation.updateStatus(ReservationStatus.EXPIRED);
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> reservation.updateStatus(ReservationStatus.CANCELED));
    assertThat(exception.getMessage()).contains("Cannot transition from ");
  }

  @Test
  void updateStatus5() {
    //
    Item item = Mockito.mock(Item.class);
    User user = Mockito.mock(User.class);
    Reservation reservation = new Reservation(item, user, LocalDateTime.now(), LocalDateTime.now());

    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> reservation.updateStatus(ReservationStatus.PENDING));
    assertThat(exception.getMessage()).contains("Unexpected value:");
  }

  @Test
  void updateStatus6() {
    //
    Item item = Mockito.mock(Item.class);
    User user = Mockito.mock(User.class);
    Reservation reservation = new Reservation(item, user, LocalDateTime.now(), LocalDateTime.now());

    reservation.updateStatus(ReservationStatus.CANCELED);
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> reservation.updateStatus(ReservationStatus.EXPIRED));
    assertThat(exception.getMessage()).contains("Cannot transition from ");
  }

  @Test
  void updateStatus7() {
    //
    Item item = Mockito.mock(Item.class);
    User user = Mockito.mock(User.class);
    Reservation reservation = new Reservation(item, user, LocalDateTime.now(), LocalDateTime.now());

    reservation.updateStatus(ReservationStatus.CANCELED);
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> reservation.updateStatus(ReservationStatus.APPROVED));
    assertThat(exception.getMessage()).contains("Cannot transition from ");
  }
}