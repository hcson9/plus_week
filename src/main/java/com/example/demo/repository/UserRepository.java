package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> 사용자 관련 Repository. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Email 로 User 검색.
     *
     * @param email 조회할 email
     * @return User.
     */
    User findByEmail(String email);

    /**
     * id 로 검색.
     *
     * @param id id
     * @return User
     * @throws IllegalArgumentException 없을시 발생
     */
    default User findUserById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID에 맞는 값이 존재하지 않습니다."));
    }

    /**
     * APPROVED 상태 PENDING 상태 변경.
     *
     * @param ids 변경할 ids
     * @return 개수
     */
    @Modifying
    @Query("UPDATE User u SET u.status = 'PENDING' WHERE u.status = 'APPROVED' AND u.id IN :ids")
    int updatePendingStatus(@Param("ids") List<Long> ids);
}
