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

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

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
