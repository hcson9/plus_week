/*
 * Created by Hochan Son on 2024. 12. 12.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.example.demo.repository;

import com.example.demo.entity.QReservation;
import com.example.demo.entity.Reservation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create on 2024. 12. 12..
 * create by IntelliJ IDEA.
 *
 * <p> 예약 관련 QueryDsl. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Repository
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public ReservationCustomRepositoryImpl(EntityManager entityManager) {
    this.jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public List<Reservation> findReservations(Long userId, Long itemId) {
    QReservation reservation = QReservation.reservation;

    return jpaQueryFactory.selectFrom(reservation)
            .where(eqUserId(reservation, userId),
                    eqItemId(reservation, itemId))
            .fetch();
  }

  /**
   * User Id 체크.
   *
   * @param reservation QClass
   * @param userId userId
   * @return 조건
   */
  private BooleanExpression eqUserId(QReservation reservation, Long userId) {
    return userId != null ? reservation.user.id.eq(userId) : null;
  }


  /**
   * ItemId 체크.
   *
   * @param reservation QClass
   * @param itemId itemId
   * @return 조건
   */
  private BooleanExpression eqItemId(QReservation reservation, Long itemId) {
    return itemId != null ? reservation.item.id.eq(itemId) : null;
  }
}
