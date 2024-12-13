/*
 * Created by Hochan Son on 2024. 12. 12.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 12.
 */

package com.example.demo.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

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
@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
  private final S3Client s3Client;

  @Value("${amazon.s3.bucket}")
  private String bucket;


  public String uploadMultipartFile(MultipartFile file) throws IOException {
    String key ="/" + file.getOriginalFilename();
    uploadInputStream(key, file.getInputStream(), file.getSize(), file.getContentType());
    return key;
  }

  /**
   * InputStream을 S3에 업로드
   *
   * @param key           S3 객체 키
   * @param inputStream   업로드할 InputStream
   * @param contentLength InputStream의 총 길이
   * @param contentType   파일의 Content-Type (예: "image/jpeg", "application/pdf")
   */
  public void uploadInputStream(String key, InputStream inputStream, long contentLength, String contentType) {
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(contentType) // Content-Type 설정
            .build();

    s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, contentLength));
  }

  public DownloadInfo download(String key) throws IOException {
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();
    // S3에서 파일 가져오기
    ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);

    // InputStream 을 byte 배열로 변환
    String contentType = responseInputStream.response().contentType();
    byte[] fileContent = responseInputStream.readAllBytes();
    responseInputStream.close();
    return new DownloadInfo(contentType, key, fileContent);
  }

  public void delete(String key) throws IOException {
    DeleteObjectRequest request = DeleteObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();
    s3Client.deleteObject(request);
  }
}
