package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> AdminService. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final UserRepository userRepository;

    /**
     * 유저 리포트 조회.
     *
     * @param userIds 조회할 ids.
     */
    // TODO: 4. find or save 예제 개선
    @Transactional
    public void reportUsers(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);

        if (CollectionUtils.isEmpty(users)) {
            throw new IllegalArgumentException("사용자가 없습니다.");
        }

        userRepository.updatePendingStatus(userIds);
    }
}
