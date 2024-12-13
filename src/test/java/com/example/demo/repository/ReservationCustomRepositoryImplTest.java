package com.example.demo.repository;/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

import com.example.demo.entity.Item;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
@DataJpaTest
@ExtendWith(MockitoExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // 스프링 컨텍스트 리프레시
class ReservationCustomRepositoryImplTest {

  @Autowired
  private ReservationRepository reservationCustomRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ItemRepository itemRepository;

  private User user1;
  private Item item;
  private Reservation reservation;

  @BeforeEach
  void setUp() {
    user1 = new User("user", "test3@example.com", "ACTIVE", "123456");

    userRepository.save(user1); // 없을 경우에만 저장

    User user2 = new User("user", "test4@example.com", "111ACTIVE", "123456");
    userRepository.save(user2);

    item = new Item("123", "!@#$", user1, user2);
    itemRepository.save(item);

    reservation = new Reservation(item, user1, LocalDateTime.now(), LocalDateTime.now());
    reservationCustomRepository.save(reservation);

  }

  @Test
  void findReservations1() {
    List<Reservation> reservationList = reservationCustomRepository.findReservations(user1.getId(), item.getId());
    assertThat(reservationList).isNotNull();
    assertThat(reservationList.get(0).getId()).isEqualTo(reservation.getId());
  }

  @Test
  void findReservations2() {
    List<Reservation> reservationList = reservationCustomRepository.findReservations(user1.getId(), null);
    assertThat(reservationList).isNotNull();
    assertThat(reservationList.get(0).getId()).isEqualTo(reservation.getId());
  }

  @Test
  void findReservations3() {
    List<Reservation> reservationList = reservationCustomRepository.findReservations(null, item.getId());
    assertThat(reservationList).isNotNull();
    assertThat(reservationList.get(0).getId()).isEqualTo(reservation.getId());
  }

  @Test
  void findReservations4() {
    List<Reservation> reservationList = reservationCustomRepository.findReservations(null, null);
    assertThat(reservationList).isNotNull();
    assertThat(reservationList.get(0).getId()).isEqualTo(reservation.getId());
  }

  @Test
  void findReservations5() {
    List<Reservation> reservationList = reservationCustomRepository.findReservations(999L, 999L);
    assertThat(reservationList).isEmpty();
  }
}