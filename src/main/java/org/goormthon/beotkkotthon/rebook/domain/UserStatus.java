package org.goormthon.beotkkotthon.rebook.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_statuses")
@DynamicUpdate
public class UserStatus {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @Column(name = "user_id")
    private UUID id;

    /* -------------------------------------------- */
    /* -------------- Setting Column -------------- */
    /* -------------------------------------------- */
    @Column(name = "is_enable_notification", nullable = false)
    private Boolean isEnableNotification;

    @Column(name = "notification_time", nullable = false)
    private LocalTime notificationTime;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public UserStatus(User user) {
        this.user = user;
        this.isEnableNotification = true;
        this.notificationTime = LocalTime.of(9, 0);
    }

    public void updateEnableNotification(Boolean isEnableNotification) {
        this.isEnableNotification = isEnableNotification;
    }

    public void updateNotificationTime(int hour, int minute) {
        this.notificationTime = LocalTime.of(hour, minute);
    }
}
