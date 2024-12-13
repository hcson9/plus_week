package com.example.demo.controller;/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

import com.example.demo.constants.GlobalConstants;
import com.example.demo.dto.Authentication;
import com.example.demo.dto.ReservationRequestDto;
import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.entity.Role;
import com.example.demo.service.ReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ReservationService reservationService;

  @Autowired
  private ObjectMapper objectMapper;

  private String baseUrl = "/reservations";

  private MockHttpSession session = new MockHttpSession();

  private Authentication authentication = Mockito.mock(Authentication.class);

  @Test
  void createReservation() throws Exception {
    ReservationRequestDto dto = new ReservationRequestDto(1L, 2L, LocalDateTime.now(), LocalDateTime.now());
    given(authentication.getRole()).willReturn(Role.USER);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    mockMvc.perform(post(baseUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto))
                    .session(session))
            .andExpectAll(status().isCreated(),
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.message").exists(),
                    jsonPath("$.message").value("success"));
  }

  @Test
  void updateReservation() throws Exception {
    Map<String, String> request = new HashMap<>();
    request.put("status", "status");
    given(authentication.getRole()).willReturn(Role.USER);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    // then
    mockMvc.perform(patch(baseUrl + "/{id}/update-status", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .session(session))
            .andExpectAll(status().isOk(),
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.message").exists(),
                    jsonPath("$.message").value("success"));

  }

  @Test
  void findAll() throws Exception {
    // given
    given(authentication.getRole()).willReturn(Role.USER);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);
    ReservationResponseDto dto = new ReservationResponseDto(1L, "123", "123", LocalDateTime.now(), LocalDateTime.now());

    // when
    when(reservationService.getReservations()).thenReturn(List.of(dto));

    // then
    mockMvc.perform(get(baseUrl)
                    .session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data[0].id").value(1L));
  }

  @Test
  void searchAll() throws Exception {
    // given
    given(authentication.getRole()).willReturn(Role.USER);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);
    ReservationResponseDto dto = new ReservationResponseDto(1L, "123", "123", LocalDateTime.now(), LocalDateTime.now());

    // when
    when(reservationService.searchAndConvertReservations(anyLong(), anyLong()))
            .thenReturn(List.of(dto));

    // then
    mockMvc.perform(get(baseUrl + "/search")
                    .param("userId", "1")
                    .param("itemId", "2")
                    .session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data[0].id").value(1L));
  }
}