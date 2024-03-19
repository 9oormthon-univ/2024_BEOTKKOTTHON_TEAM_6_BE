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
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"serial_id", "provider"})
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

    @Column(name = "serial_id", nullable = false)
    private String serialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
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

    /* -------------------------------------------- */
    /* -------------- Security Column ------------- */
    /* -------------------------------------------- */
    @Column(name = "refresh_Token")
    private String refreshToken;


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
        this.serialId = serialId;
        this.provider = provider;
        this.role = role;
        this.nickname = nickname;
        this.password = password;
        this.environmentalTemperature = 36.5f;
        this.createdAt = LocalDate.now();
        this.refreshToken = null;
    }
}
