package com.example.demo.controller;

import com.example.demo.dto.CommonResponseBody;
import com.example.demo.dto.ReservationRequestDto;
import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * create on 2024. 12. 13. create by IntelliJ IDEA.
 * create by IntelliJ IDEA.
 *
 * <p> 예약 관련 Controller. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;


    /**
     * 예약 생성.
     *
     * @param reservationRequestDto 생성 요청
     * @return 성공 유무.
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<Void>> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        reservationService.createReservation(reservationRequestDto.getItemId(),
                                            reservationRequestDto.getUserId(),
                                            reservationRequestDto.getStartAt(),
                                            reservationRequestDto.getEndAt());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponseBody<>("success"));
    }

    /**
     * 상태값 변경.
     *
     * @param id 변경할 id
     * @param status 상태
     * @return 성공유무
     */
    @PatchMapping("/{id}/update-status")
    public ResponseEntity<CommonResponseBody<Void>> updateReservation(@PathVariable Long id, @RequestBody String status) {
        reservationService.updateReservationStatus(id, status);
        return ResponseEntity.ok()
                .body(new CommonResponseBody<>("success"));
    }

    /**
     * 전체검색.
     *
     * @return 검색 결과
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<List<ReservationResponseDto>>> findAll() {
        return ResponseEntity.ok()
                .body(new CommonResponseBody<>("success", reservationService.getReservations()));
    }

    /**
     * 조건 검색.
     *
     * @param userId 조회할 user id
     * @param itemId 조회할 item id
     * @return 조회 결과
     */
    @GetMapping("/search")
    public ResponseEntity<CommonResponseBody<List<ReservationResponseDto>>> searchAll(@RequestParam(required = false) Long userId,
                          @RequestParam(required = false) Long itemId) {
        List<ReservationResponseDto> dtos = reservationService.searchAndConvertReservations(userId, itemId);
        return ResponseEntity.ok()
                .body(new CommonResponseBody<>("success", dtos));
    }
}
