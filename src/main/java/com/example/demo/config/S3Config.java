/*
 * Created by Hochan Son on 2024. 12. 12.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 12.
 */

package com.example.demo.config;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;
import java.net.URISyntaxException;

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
@Configuration
public class S3Config {

  @Value("${amazon.s3.region}")
  private String region;

  @Value("${amazon.s3.url}")
  private String url;

  @Value("${amazon.s3.access-key}")
  private String accessKey;

  @Value("${amazon.s3.secret-key}")
  private String secretKey;

  @Bean
  public S3Client getS3Client() {
    return S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials(accessKey, secretKey)))
//            .serviceConfiguration(S3Configuration.builder()
//                    .pathStyleAccessEnabled(true)
//                    .build())
//            .forcePathStyle(true) // 필요 시 경로 스타일 사용
            .endpointOverride(customEndpoint())
            .region(Region.of(region))
            .build();
  }

  private AwsCredentials awsCredentials(String accessKey, String secretKey) {
    return AwsBasicCredentials.builder()
            .accessKeyId(accessKey)
            .secretAccessKey(secretKey)
            .build();
  }

  /**
   * Minio or 다른 플랫폼 사용시 적용.
   *
   * @return URI or Null
   */
  private URI customEndpoint() {
    // customEndpoint가 설정되어 있으면 사용, 없으면 기본 설정
    try {
      return url != null ? new URI(url) : null;
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid endpoint URI", e);
    }
  }
}
