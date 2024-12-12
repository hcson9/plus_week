package com.example.demo.controller;

import com.example.demo.dto.CommonResponseBody;
import com.example.demo.dto.ReservationRequestDto;
import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;


    @PostMapping
    public ResponseEntity<CommonResponseBody<Void>> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        reservationService.createReservation(reservationRequestDto.getItemId(),
                                            reservationRequestDto.getUserId(),
                                            reservationRequestDto.getStartAt(),
                                            reservationRequestDto.getEndAt());
        return ResponseEntity.ok()
                .body(new CommonResponseBody<>("success"));
    }

    @PatchMapping("/{id}/update-status")
    public ResponseEntity<CommonResponseBody<Void>> updateReservation(@PathVariable Long id, @RequestBody String status) {
        reservationService.updateReservationStatus(id, status);
        return ResponseEntity.ok()
                .body(new CommonResponseBody<>("success"));
    }

    @GetMapping
    public ResponseEntity<CommonResponseBody<List<ReservationResponseDto>>> findAll() {
        return ResponseEntity.ok()
                .body(new CommonResponseBody<>("success", reservationService.getReservations()));
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponseBody<List<ReservationResponseDto>>> searchAll(@RequestParam(required = false) Long userId,
                          @RequestParam(required = false) Long itemId) {
        return ResponseEntity.ok()
                .body(new CommonResponseBody<>("success", reservationService.searchAndConvertReservations(userId, itemId)));
    }
}
