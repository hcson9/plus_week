package com.example.demo.config;/*
 * Created by Hochan Son on 2024. 12. 13.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 13.
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

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
class S3ConfigTest {

  @InjectMocks
  private S3Config s3Config;

  private String region = Region.US_WEST_2.toString();
  private String url = "http://localhost:9000"; // 예시 URL (MinIO 또는 다른 S3 호환 플랫폼)
  private String accessKey = "accessKey";
  private String secretKey = "secretKey";

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(s3Config, "region", region);
    ReflectionTestUtils.setField(s3Config, "accessKey", accessKey);
    ReflectionTestUtils.setField(s3Config, "secretKey", secretKey);
  }

  @Test
  void getS3Client() {
    ReflectionTestUtils.setField(s3Config, "url", url);
    assertDoesNotThrow(() -> s3Config.getS3Client());
  }

  @Test
  void getS3Client2() {
    ReflectionTestUtils.setField(s3Config, "url", null);
    assertDoesNotThrow(() -> s3Config.getS3Client());
  }

  @Test
  void getS3Client3() {
    ReflectionTestUtils.setField(s3Config, "url", "http://example.com:80/  /path");
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> s3Config.getS3Client());
    assertThat(exception.getMessage()).contains("Invalid endpoint URI");
  }
}