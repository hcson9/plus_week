package com.example.demo.controller;

import com.example.demo.dto.CommonResponseBody;
import com.example.demo.dto.ItemRequestDto;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> Item Controller. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

  /**
   * 아이템 생성.
   *
   * @param itemRequestDto 아이템 생성 요청
   * @return 성공유무
   */
  @PostMapping
    public ResponseEntity<CommonResponseBody<Void>> createItem(@RequestBody ItemRequestDto itemRequestDto) {
         itemService.createItem(itemRequestDto.getName(),
                                itemRequestDto.getDescription(),
                                itemRequestDto.getOwnerId(),
                                itemRequestDto.getManagerId());
         return ResponseEntity.status(HttpStatus.CREATED)
                 .body(new CommonResponseBody<>("success"));
    }
}
