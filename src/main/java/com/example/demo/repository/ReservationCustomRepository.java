/*
 * Created by Hochan Son on 2024. 12. 12.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.example.demo.repository;

import com.example.demo.entity.Reservation;

import java.util.List;

/**
 * create on 2024. 12. 12..
 * create by IntelliJ IDEA.
 *
 * <p> Reservation Custom Repository. </p>
 * <p> {@link ReservationRepository} 관련 클래스 </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @see com.example.demo.service.ReservationService
 * @since 1.0
 */
public interface ReservationCustomRepository {

  /**
   * Reservations 검색.
   *
   * @param userId 검색조건 1
   * @param itemId 검색조건 2
   * @return 조회된 {@code List<Reservation>}
   */
  List<Reservation> findReservations(Long userId, Long itemId);

}
