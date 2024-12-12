/*
 * Created by Hochan Son on 2024. 12. 12.
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Dev Backend Team <hochan@bigin.io>, 2024. 12. 12.
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
 * <p> 클래스 설명 </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @see
 * @since 지원하는 자바버전 (ex : 5+ 5이상)
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
            .where(eqUserId(reservation, userId)
                    .and(eqItemId(reservation, itemId)))
            .fetch();
  }

  private BooleanExpression eqUserId(QReservation reservation, Long userId) {
    return userId != null ? reservation.user.id.eq(userId) : null;
  }

  private BooleanExpression eqItemId(QReservation reservation, Long itemId) {
    return itemId != null ? reservation.item.id.eq(itemId) : null;
  }
}
