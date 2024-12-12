package com.example.demo.service;

import com.example.demo.entity.RentalLog;
import com.example.demo.repository.RentalLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalLogService {
    private final RentalLogRepository rentalLogRepository;


    @Transactional
    public void save(RentalLog rentalLog) {
        rentalLogRepository.save(rentalLog);
        if (rentalLog == null) {
            throw new RuntimeException();
        }
    }
}
