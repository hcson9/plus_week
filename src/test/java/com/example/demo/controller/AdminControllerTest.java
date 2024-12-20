package com.example.demo.controller;/*
 * Created by Hochan Son on 2024. 12. 12.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

import com.example.demo.constants.GlobalConstants;
import com.example.demo.dto.Authentication;
import com.example.demo.dto.ReportRequestDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

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
@WebMvcTest(AdminController.class)
class AdminControllerTest {
  @MockitoBean
  private AdminService adminService;

  @Autowired
  private MockMvc mvc;

  private final String baseUrl = "/admins";

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void reportUsersTest() throws Exception {

    // given
    List<Long> ids = List.of(1L, 2L, 3L);
    List<User> users = new ArrayList<>();
    users.add(Mockito.mock(User.class));
    users.add(Mockito.mock(User.class));

    ReportRequestDto reportRequestDto = new ReportRequestDto(ids);

    MockHttpSession session = new MockHttpSession();
    Authentication authentication = new Authentication(1L, Role.ADMIN);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    // then
    mvc.perform(post(baseUrl + "/report-users")
                    .content(objectMapper.writeValueAsString(reportRequestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(session))
            .andExpectAll(status().isOk(),
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.message").exists());
  }
}