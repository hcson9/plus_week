package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.entity.User;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createItem(String name, String description, Long ownerId, Long managerId) {
        User owner = userRepository.findUserById(ownerId);
        User manager = userRepository.findUserById(managerId);

        Item item = new Item(name, description, owner, manager);
        itemRepository.save(item);
    }
}
