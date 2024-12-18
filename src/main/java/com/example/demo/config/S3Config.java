/*
 * Created by HoChan Son on 2024. 12. 12.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.example.demo.config;



import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * create on 2024. 12. 12.
 * create by IntelliJ IDEA.
 *
 * <p> S3 관련 Configuration. </p>
 * <p> {@link FileService} 관련 클래스 </p>
 *
 * @author Hochan Son
 * @version 1.0
 */
@Configuration
public class S3Config {

  /**
   * Region.
   */
  @Value("${amazon.s3.region}")
  private String region;

  /**
   * minio or ncloud 등 사용시엔 url 지정.
   */
  @Value("${amazon.s3.url}")
  private String url;

  /**
   * accessKey.
   */
  @Value("${amazon.s3.access-key}")
  private String accessKey;


  /**
   * secretKey.
   */
  @Value("${amazon.s3.secret-key}")
  private String secretKey;

  @Value("${amazon.s3.force-path:false}")
  private boolean forcePath;

  /**
   * S3 관련 객체.
   * <pre>
   *   - forcePathStyle 의 경우 minio 의 경우에 필요하다.
   *   - {{bucketName}}.s3.amazonaws.com 이 현재 사용 기준
   *   - s3.amazonaws.com/{{bucketName}}/ 와 같은 방식은 예전기준 -> minio 에서 사용됨.
   * </pre>
   * @return S3Client
   */
  @Bean
  public S3Client getS3Client() {
    return S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials(accessKey, secretKey)))
            .forcePathStyle(forcePath) // 필요 시 경로 스타일 사용
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
