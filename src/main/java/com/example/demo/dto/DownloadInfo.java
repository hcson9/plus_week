/*
 * Created by Hochan Son on 2024. 12. 12.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.example.demo.dto;

import lombok.Getter;

/**
 * create on 2024. 12. 12..
 * create by IntelliJ IDEA.
 *
 * <p> 파일 정보. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Getter
public class DownloadInfo {

  /**
   * Content-type.
   */
  private String contentType;

  /**
   * 파일 명.
   */
  private String fileName;

  /**
   * Content-length.
   */
  private Long fileSize;

  /**
   * data.
   */
  private byte[] data;

  /**
   * 생성자.
   *
   * @param contentType ContentType
   * @param fileName 파일 명
   * @param fileSize content-length
   * @param data data
   */
  public DownloadInfo(String contentType, String fileName, Long fileSize, byte[] data) {
    this.contentType = contentType;
    this.fileName = fileName;
    this.fileSize = fileSize;
    this.data = data;
  }
}
