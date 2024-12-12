package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status; // PENDING, APPROVED, CANCELED, EXPIRED

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RentalLog> rentalLogList = new ArrayList<>();

    public Reservation(Item item, User user, LocalDateTime startAt, LocalDateTime endAt) {
        this.item = item;
        this.user = user;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = ReservationStatus.PENDING;
    }

    public void updateStatus(ReservationStatus newStatus) {
        if (!isApproveUpdateStatus(newStatus)) {
            throw new IllegalStateException(String.format("Cannot transition from %s to %s", this.status, newStatus));
        }
        this.status = newStatus;
    }

    private boolean isApproveUpdateStatus(ReservationStatus status) {
        return switch (status) {
            case APPROVED, EXPIRED -> this.status == ReservationStatus.PENDING;
          case CANCELED -> this.status != ReservationStatus.EXPIRED;
          default -> throw new IllegalStateException("Unexpected value: " + status);
        };
    }

    public void addRentalLog(RentalLog rentalLog) {
        this.rentalLogList.add(rentalLog);
    }
}
