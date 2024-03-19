package org.goormthon.beotkkotthon.rebook.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goormthon.beotkkotthon.rebook.dto.type.EProvider;
import org.goormthon.beotkkotthon.rebook.dto.type.ERole;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_serial_id_provider",
                columnNames = {"serial_id", "provider"}
        )
})
@DynamicUpdate
public class User {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "serial_id", nullable = false, updatable = false)
    private String serialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, updatable = false)
    private EProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ERole role;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name =  "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "environmental_temperature", nullable = false)
    private Float environmentalTemperature;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "device_Token")
    private String deviceToken;

    /* -------------------------------------------- */
    /* -------------- Security Column ------------- */
    /* -------------------------------------------- */
    @Column(name = "refresh_Token")
    private String refreshToken;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<StudyHistory> studyHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ChallengeRoomUser> challengeRoomUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<QuizHistory> quizHistories = new ArrayList<>();

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public User(
            String serialId,
            EProvider provider,
            ERole role,
            String nickname,
            String password
    ) {
        // Default Column
        this.serialId = serialId;
        this.provider = provider;
        this.role = role;

        // Information Column
        this.nickname = nickname;
        this.password = password;
        this.environmentalTemperature = 17.0f;
        this.createdAt = LocalDate.now();
        this.deviceToken = null;

        // Security Column
        this.refreshToken = null;
    }

    public void updateEnvironmentalTemperature(Float environmentalTemperature) {
        this.environmentalTemperature = environmentalTemperature;
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
