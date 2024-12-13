package com.example.demo.controller;/*
 * Created by Hochan Son on 2024. 12. 12.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

import com.example.demo.constants.GlobalConstants;
import com.example.demo.dto.Authentication;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.entity.Role;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;


  private String baseUrl = "/users";

  @Test
  void signupWithEmail() throws Exception {
    UserRequestDto requestDto = new UserRequestDto("user", "123@123.com", "test", "1234");

    mockMvc.perform(post(baseUrl)
                    .content(objectMapper.writeValueAsString(requestDto)).contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(status().isCreated(),
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.message").exists());
  }

  @Test
  void loginWithEmail() throws Exception {
    LoginRequestDto requestDto = new LoginRequestDto("user", "1234");
    MockHttpSession session = new MockHttpSession();

    mockMvc.perform(post(baseUrl + "/login")
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(session))
            .andExpectAll(status().isOk(),
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.message").exists());
  }

  @Test
  void logout() throws Exception {
    MockHttpSession session = new MockHttpSession();
    Authentication authentication = new Authentication(1L, Role.USER);
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    mockMvc.perform(post(baseUrl + "/logout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(session))
            .andExpectAll(status().isOk(),
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$.message").exists());
  }
}