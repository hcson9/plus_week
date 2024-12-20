package com.example.demo.repository;

import com.example.demo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> ItemRepository. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

  /**
   * id 로 검색.
   *
   * @param itemId id
   * @return Item
   * @throws IllegalArgumentException 없을시 발생
   */
  default Item findByItemId(Long itemId) {
    return findById(itemId).orElseThrow(() -> new IllegalArgumentException("해당 ID에 맞는 값이 존재하지 않습니다."));
  }
}
