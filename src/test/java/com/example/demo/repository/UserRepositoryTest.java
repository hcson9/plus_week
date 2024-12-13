package com.example.demo.repository;/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

import com.example.demo.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

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
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  // 스프링 컨텍스트 리프레시
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Mock
  private UserRepository mockedUserRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  private User user;
  private User user2;

  @BeforeEach
  void setUp() {
    user = new User("user", "test@example.com", "ACTIVE", "123456");
    testEntityManager.persistAndFlush(user);

    user2 = new User("user", "test11@example.com", "111ACTIVE", "123456");
    testEntityManager.persistAndFlush(user2);
  }

  @Test
  void testFindByEmail() {
    // Email로 사용자를 찾는 테스트
    User foundUser = userRepository.findByEmail("test@example.com");
    assertNotNull(foundUser);
    assertEquals("test@example.com", foundUser.getEmail());
  }

  @Test
  void testFindUserById_Success() {
    // ID로 사용자를 찾는 테스트
    User foundUser = userRepository.findUserById(1L);
    assertNotNull(foundUser);
    assertEquals(1L, foundUser.getId());
  }

  @Test
  void testFindUserById_Fail() {
    // 존재하지 않는 ID로 사용자를 찾는 경우 예외가 발생하는지 테스트
    InvalidDataAccessApiUsageException thrown = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
      userRepository.findUserById(999L); // 존재하지 않는 ID
    });
    assertEquals("해당 ID에 맞는 값이 존재하지 않습니다.", thrown.getMessage());
  }
  @Test
  void updatePendingStatusTest() {
    int i = userRepository.updatePendingStatus(List.of(2L));
    assertNotNull(i);
  }
}