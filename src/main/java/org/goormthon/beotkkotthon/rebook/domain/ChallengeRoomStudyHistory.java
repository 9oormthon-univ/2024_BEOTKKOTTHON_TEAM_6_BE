package org.goormthon.beotkkotthon.rebook.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "challenge_room_study_histories", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_challenge_room_study_history",
                columnNames = {"challenge_room_id", "study_history_id"}
        )
})
public class ChallengeRoomStudyHistory {
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
    @JoinColumn(name = "challenge_room_id", nullable = false)
    private ChallengeRoom challengeRoom;

    @ManyToOne
    @JoinColumn(name = "study_history_id", nullable = false)
    private StudyHistory studyHistory;

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public ChallengeRoomStudyHistory(
            ChallengeRoom challengeRoom,
            StudyHistory studyHistory
    ) {
        this.challengeRoom = challengeRoom;
        this.studyHistory = studyHistory;
    }
}
