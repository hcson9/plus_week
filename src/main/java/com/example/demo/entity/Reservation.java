package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * create on 2024. 12. 20.
 * create by IntelliJ IDEA.
 *
 * <p> 예약 Entity. </p>
 *
 * @author Hochan Son
 * @version 1.0
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * item.
     */
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    /**
     * user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 시작날짜.
     */
    private LocalDateTime startAt;

    /**
     * 종료날짜.
     */
    private LocalDateTime endAt;

    /**
     * 상태값.
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * 대여 로그.
     */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RentalLog> rentalLogList = new ArrayList<>();

    /**
     * 생성자.
     *
     * @param item 아이템
     * @param user 유저
     * @param startAt 시작일자
     * @param endAt 종료일자
     */
    public Reservation(Item item, User user, LocalDateTime startAt, LocalDateTime endAt) {
        this.item = item;
        this.user = user;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = Status.PENDING;
    }

    /**
     * 상태값 변경.
     *
     * @param newStatus 변경할 상태
     * @IllegalStateException 잘못된 상태일때 발생
     */
    public void updateStatus(Status newStatus) {
        if (!isApproveUpdateStatus(newStatus)) {
            throw new IllegalStateException(String.format("Cannot transition from %s to %s", this.status, newStatus));
        }
        this.status = newStatus;
    }

    /**
     * 상태 변경여부체크.
     *
     * @param status 변경할 status
     * @return 변경 가능여부
     */
    private boolean isApproveUpdateStatus(Status status) {
        return switch (status) {
            case APPROVED, EXPIRED -> this.status == Status.PENDING;
            case CANCELED -> this.status != Status.EXPIRED;
            default -> throw new IllegalStateException("Unexpected value: " + status);
        };
    }

    /**
     * 대여로그 추가.
     *
     * @param rentalLog 추가할 대여로그
     */
    public void addRentalLog(RentalLog rentalLog) {
        this.rentalLogList.add(rentalLog);
    }
}
