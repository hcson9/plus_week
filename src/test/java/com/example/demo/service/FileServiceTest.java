package com.example.demo.service;/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

import com.example.demo.dto.DownloadInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
@ExtendWith(MockitoExtension.class)
class FileServiceTest {
  @Mock
  private S3Client s3Client;

  @InjectMocks
  private FileService fileService;

  @Mock
  private MultipartFile multipartFile;
  private String bucketName = "test-bucket";
  private String key = "/testFile.txt";
  private byte[] fileContent = "test content".getBytes(StandardCharsets.UTF_8);

  @Test
  void uploadMultipartFile() throws IOException {
    when(multipartFile.getOriginalFilename()).thenReturn("testFile.txt");
    when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));
    when(multipartFile.getSize()).thenReturn((long) fileContent.length);
    when(multipartFile.getContentType()).thenReturn("text/plain");

    String returnedKey = fileService.uploadMultipartFile(multipartFile);
    // 호출된 메서드의 결과값 검증
    assertEquals(key, returnedKey);

    // S3Client의 putObject가 호출되었는지 검증
    ArgumentCaptor<PutObjectRequest> captor = ArgumentCaptor.forClass(PutObjectRequest.class);
    verify(s3Client).putObject(captor.capture(), (RequestBody) any());
    PutObjectRequest request = captor.getValue();
    assertEquals(key, request.key());
    assertEquals("text/plain", request.contentType());
  }

  @Test
  void download() throws IOException {
    GetObjectResponse response = GetObjectResponse.builder()
            .contentType("text/plain")
            .build();

    when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(new ResponseInputStream<>(response, new ByteArrayInputStream(fileContent)));


    DownloadInfo downloadInfo = fileService.download(key);
    assertThat(downloadInfo).isNotNull();
    assertThat(downloadInfo.getFileName()).contains(key);
    assertThat(downloadInfo.getContentType()).isEqualTo("text/plain");
  }

  @Test
  void delete() {
    // then
    assertDoesNotThrow(() -> fileService.delete(key));

    // S3client 에서 deleteObject 호출되었는지 검증
    ArgumentCaptor<DeleteObjectRequest> captor = ArgumentCaptor.forClass(DeleteObjectRequest.class);
    verify(s3Client).deleteObject(captor.capture());
    DeleteObjectRequest request = captor.getValue();
    assertEquals(key, request.key());
  }
}