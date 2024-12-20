package com.example.demo.repository;

import com.example.demo.entity.RentalLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> RentalLogRepository. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface RentalLogRepository extends JpaRepository<RentalLog, Long> {
}
