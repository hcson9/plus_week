package com.example.demo.controller;/*
 * Created by Hochan Son on 2024. 12. 12.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 12.
 */

import com.example.demo.dto.ReportRequestDto;
import com.example.demo.entity.User;
import com.example.demo.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
class AdminControllerTest {

  private MockMvc mvc;

  @InjectMocks
  private AdminController adminController;

  @Mock
  private AdminService adminService;

  private final String baseUrl = "/admins";

  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.standaloneSetup(adminController).build();
  }

  @Test
  void reportUsersTest() throws Exception {
    List<Long> ids = List.of(1L, 2L, 3L);
    List<User> users = new ArrayList<>();
    users.add(Mockito.mock(User.class));
    users.add(Mockito.mock(User.class));
    ReportRequestDto reportRequestDto = new ReportRequestDto(ids);

    mvc.perform(post(baseUrl + "/report-users")
                    .content(objectMapper.writeValueAsString(reportRequestDto))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(status().isOk(),
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.message").exists());

  }
}