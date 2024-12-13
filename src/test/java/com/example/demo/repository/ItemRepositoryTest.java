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
import com.example.demo.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;

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
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  // 스프링 컨텍스트 리프레시
class ItemRepositoryTest {

  @Autowired
  private ItemRepository itemRepository;

  @BeforeEach
  void setUp() {
    Item item = new Item("123", "!@#$", Mockito.mock(User.class), Mockito.mock(User.class));
    itemRepository.save(item);
  }

  @Test
  void findByItemId() {
    Item item = itemRepository.findByItemId(1L);

    assertNotNull(item);
  }

  @Test
  void findByItemIdError() {
    InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> itemRepository.findByItemId(2L));
    assertThat(exception.getMessage()).isEqualTo("해당 ID에 맞는 값이 존재하지 않습니다.");
  }
}