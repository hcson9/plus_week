package com.example.demo.service;

import com.example.demo.entity.RentalLog;
import com.example.demo.repository.RentalLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> 예약 로그.. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalLogService {
    private final RentalLogRepository rentalLogRepository;


    /**
     * 저장.
     *
     * @param rentalLog 저장할 log
     */
    @Transactional
    public void save(RentalLog rentalLog) {
        rentalLogRepository.save(rentalLog);
        if (rentalLog == null) {
            throw new RuntimeException();
        }
    }
}
