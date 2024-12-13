package com.example.demo;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Item 컬럼 null 체크")
    void contextLoads() {
        Item item = new Item();
        assertDoesNotThrow(() -> itemRepository.save(item));
    }


}
