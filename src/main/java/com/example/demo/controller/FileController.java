package com.example.demo.controller;

import com.example.demo.dto.CommonResponseBody;
import com.example.demo.dto.FileDeleteRequest;
import com.example.demo.dto.FileDownloadRequest;
import com.example.demo.dto.DownloadInfo;
import com.example.demo.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * create on 2024/05/10 create by IntelliJ IDEA.
 *
 * <p> File Controller. </p>
 *
 * @author HoChan Son (hcson)
 * @version 1.0
 */
@RestController
@RequestMapping("/files")
@Slf4j
@RequiredArgsConstructor
public class FileController {

  private final FileService fileService;

  /**
   * 파일 업로드.
   *
   * @param files 업로드할 file
   * @return 파일 경로
   */
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<CommonResponseBody<List<String>>> upload(
          @RequestParam("files") MultipartFile[] files) throws IOException {
    List<String> keys = new ArrayList<>();
    for (MultipartFile file : files) {
      if (file.getContentType() != null) {
        keys.add(fileService.uploadMultipartFile(file));
      }
    }

    return ResponseEntity.ok()
            .body(new CommonResponseBody<>("success", keys));
  }

  /**
   * 파일 다운로드.
   *
   * @param request 생성 요청
   * @return 파일
   * @throws IOException 파일 없을 시 발생
   */
  @PostMapping(value = "/download")
  public ResponseEntity<byte[]> download(@RequestBody FileDownloadRequest request)
          throws IOException {
    DownloadInfo downloadInfo = fileService.download(request.getKey());
    byte[] fileContent = downloadInfo.getData();

    // Response Headers 설정
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
            + downloadInfo.getFileName());
    headers.add(HttpHeaders.CONTENT_TYPE, downloadInfo.getContentType());

    return ResponseEntity.ok()
            .headers(headers)
            .contentLength(downloadInfo.getFileSize())
            .body(fileContent);
  }

  /**
   * 파일 삭제.
   *
   * @param request 삭제요청
   * @return 파일
   * @throws IOException 파일 없을 시 발생
   */
  @DeleteMapping
  public ResponseEntity<CommonResponseBody<String>> delete(@RequestBody FileDeleteRequest request)
          throws IOException {
    fileService.delete(request.getKey());
    return ResponseEntity.ok()
            .body(new CommonResponseBody("success"));
  }

  @ExceptionHandler(NoSuchKeyException.class)
  public ResponseEntity<CommonResponseBody<String>> noClassDefFoundError(NoSuchKeyException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new CommonResponseBody<>("Not Found File"));
  }
}
