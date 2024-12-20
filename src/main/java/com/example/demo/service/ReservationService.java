package com.example.demo.service;

import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.entity.*;
import com.example.demo.exception.ReservationConflictException;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> 예약관련 Service. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    /**
     * 예약 생성.
     *
     * @param itemId 아이템 Id
     * @param userId 유저 id
     * @param startAt 시작일
     * @param endAt 종료일
     */
    // TODO: 1. 트랜잭션 이해
    @Transactional
    public void createReservation(Long itemId, Long userId, LocalDateTime startAt, LocalDateTime endAt) {
        // 쉽게 데이터를 생성하려면 아래 유효성검사 주석 처리
        List<Reservation> haveReservations = reservationRepository.findConflictingReservations(itemId, startAt, endAt);
        if(!haveReservations.isEmpty()) {
            throw new ReservationConflictException("해당 물건은 이미 그 시간에 예약이 있습니다.");
        }

        Item item = itemRepository.findByItemId(itemId);
        User user = userRepository.findUserById(userId);

        Reservation reservation = new Reservation(item, user, startAt, endAt);
        Reservation savedReservation = reservationRepository.save(reservation);

        RentalLog rentalLog = new RentalLog(savedReservation, "로그 메세지", "CREATE");
        reservation.addRentalLog(rentalLog);
    }

    /**
     * 예약조회.
     *
     * @return 조회된 예약정보
     */
    // TODO: 3. N+1 문제
    public List<ReservationResponseDto> getReservations() {
        List<Reservation> reservations = reservationRepository.findAllWithUserAndItem();

        return reservations.stream().map(reservation -> {
            User user = reservation.getUser();
            Item item = reservation.getItem();

            return new ReservationResponseDto(
                    reservation.getId(),
                    user.getNickname(),
                    item.getName(),
                    reservation.getStartAt(),
                    reservation.getEndAt()
            );
        }).toList();
    }

    /**
     * 예약 검색.
     *
     * @param userId userId
     * @param itemId item Id
     * @return 조회된 예약 정보
     */
    public List<ReservationResponseDto> searchAndConvertReservations(Long userId, Long itemId) {
        List<Reservation> reservations = reservationRepository.findReservations(userId, itemId);
        return convertToDto(reservations);
    }

    /**
     * Entity List to Dtos.
     *
     * @param reservations 변경할 entities
     * @return dtos
     */
    private List<ReservationResponseDto> convertToDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> new ReservationResponseDto(
                        reservation.getId(),
                        reservation.getUser().getNickname(),
                        reservation.getItem().getName(),
                        reservation.getStartAt(),
                        reservation.getEndAt()
                ))
                .toList();
    }

    /**
     * 상태값 변경.
     *
     * @param reservationId 변경할 id
     * @param status 변경할 상태
     */
    @Transactional
    public void updateReservationStatus(Long reservationId, String status) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID에 맞는 데이터가 존재하지 않습니다."));
        reservation.updateStatus(Status.valueOf(status));
    }
}
