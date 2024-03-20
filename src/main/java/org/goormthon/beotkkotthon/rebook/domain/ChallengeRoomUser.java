package org.goormthon.beotkkotthon.rebook.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "challenge_room_users", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_challenge_room_user",
                columnNames = {"challenge_room_id", "user_id"}
        )
})
public class ChallengeRoomUser {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_room_id", nullable = false, updatable = false)
    private ChallengeRoom challengeRoom;

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public ChallengeRoomUser(
            User user,
            ChallengeRoom challengeRoom
    ) {
        // Relation Column
        this.user = user;
        this.challengeRoom = challengeRoom;
    }
}
