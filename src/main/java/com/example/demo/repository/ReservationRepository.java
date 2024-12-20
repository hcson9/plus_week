package com.example.demo.repository;

import com.example.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> ReservationRepository. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationCustomRepository {

    /**
     * fetch join 으로 검색.
     *
     * @return 조회된 {@code List<Reservation>}
     */
    @Query("SELECT r FROM Reservation r JOIN FETCH r.user JOIN FETCH r.item")
    List<Reservation> findAllWithUserAndItem();


    /**
     * 해당 기간 예약 있는지 확인.
     *
     * @param id id
     * @param startAt 시작날짜
     * @param endAt 종료날짜
     * @return 조회된 {@code List<Reservation>}
     */
    @Query("SELECT r FROM Reservation r " +
            "WHERE r.item.id = :id " +
            "AND NOT (r.endAt <= :startAt OR r.startAt >= :endAt) " +
            "AND r.status = 'APPROVED'")
    List<Reservation> findConflictingReservations(
            @Param("id") Long id,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt
    );
}
