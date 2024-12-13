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
import com.example.demo.dto.FileDeleteRequest;
import com.example.demo.dto.FileDownloadRequest;
import com.example.demo.entity.Role;
import com.example.demo.file.DownloadInfo;
import com.example.demo.file.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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
@WebMvcTest(FileController.class)
@ExtendWith(MockitoExtension.class)
class FileControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private FileService fileService;

  @Autowired
  private ObjectMapper objectMapper;

  private String baseUrl = "/files";

  @Test
  void upload() throws Exception {
    // Mock 파일 데이터 생성
    MockMultipartFile file1 = new MockMultipartFile("files", "file1.txt", "text/plain", "Hello World".getBytes());
    MockMultipartFile file2 = new MockMultipartFile("files", "file2.txt", null, "Another File".getBytes());
    Authentication authentication = Mockito.mock(Authentication.class);
    MockHttpSession session = new MockHttpSession();
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    when(fileService.uploadMultipartFile(any())).thenReturn("fileKey1", "fileKey2");

    mockMvc.perform(MockMvcRequestBuilders.multipart(baseUrl)
                    .file(file1)
                    .file(file2)
                    .session(session))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("success"))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0]").value("fileKey1"));

    verify(fileService, times(1)).uploadMultipartFile(any());
  }

  @Test
  void download() throws Exception {
    String fileKey = "sampleKey";
    DownloadInfo mockDownloadInfo = new DownloadInfo("text/plain", "file.txt", 14L, "Sample Content".getBytes());
    Authentication authentication = Mockito.mock(Authentication.class);
    MockHttpSession session = new MockHttpSession();
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    when(fileService.download(fileKey)).thenReturn(mockDownloadInfo);

    FileDownloadRequest request = new FileDownloadRequest(fileKey);

    mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/download")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .session(session))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Disposition", "attachment; filename=file.txt"))
            .andExpect(header().string("Content-Type", "text/plain"))
            .andExpect(content().bytes("Sample Content".getBytes()));

    verify(fileService).download(fileKey);
  }

  @Test
  void delete() throws Exception {
    // Mock 데이터 생성
    String fileKey = "fileToDelete";
    FileDeleteRequest request = new FileDeleteRequest(fileKey);
    Authentication authentication = Mockito.mock(Authentication.class);
    MockHttpSession session = new MockHttpSession();
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    doNothing().when(fileService).delete(fileKey);

    mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .session(session))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("success"));

    verify(fileService).delete(fileKey);
  }

  @Test
  void noClassDefFoundError() throws Exception {
    // Mock 예외 발생
    String fileKey = "nonExistentKey";
    FileDeleteRequest request = new FileDeleteRequest(fileKey);
    Authentication authentication = Mockito.mock(Authentication.class);
    MockHttpSession session = new MockHttpSession();
    session.setAttribute(GlobalConstants.USER_AUTH, authentication);

    doThrow(NoSuchKeyException.builder().build())
            .when(fileService).delete(fileKey);

    mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .session(session))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Not Found File"));

    verify(fileService).delete(fileKey);
  }
}