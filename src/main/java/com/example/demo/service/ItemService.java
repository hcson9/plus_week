package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.entity.User;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> Item 관련 Service. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    /**
     * 아이템 생성.
     *
     * @param name 이름
     * @param description 설명
     * @param ownerId owner id
     * @param managerId manager id
     */
    @Transactional
    public void createItem(String name, String description, Long ownerId, Long managerId) {
        User owner = userRepository.findUserById(ownerId);
        User manager = userRepository.findUserById(managerId);

        Item item = new Item(name, description, owner, manager);
        itemRepository.save(item);
    }
}
