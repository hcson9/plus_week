package com.example.demo.service;/*
 * Created by Hochan Son on 2024. 12. 12.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

import com.example.demo.entity.Item;
import com.example.demo.entity.User;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
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
class ItemServiceTest {

  @InjectMocks
  private ItemService itemService;

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private UserRepository userRepository;

  @Test
  void createItemTest() {
    // given
    Long ownerId = 1L;
    Long managerId = 2L;
    User owner = Mockito.mock(User.class);
    User manager = Mockito.mock(User.class);

    // when
    when(userRepository.findUserById(ownerId)).thenReturn(owner);
    when(userRepository.findUserById(managerId)).thenReturn(manager);

    // then
    assertDoesNotThrow(() -> itemService.createItem("사과", "안녕하세요", ownerId, managerId));
  }
}