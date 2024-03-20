package org.goormthon.beotkkotthon.rebook.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "challenge_categories", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_challenge_recycle_category",
                columnNames = {"challenge_id", "recycle_category_id"}
        )
})
public class ChallengeCategory {
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
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "recycle_category_id", nullable = false)
    private RecycleCategory recycleCategory;

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public ChallengeCategory(
            Challenge challenge,
            RecycleCategory recycleCategory
    ) {
        // Relation Column
        this.challenge = challenge;
        this.recycleCategory = recycleCategory;
    }
}
