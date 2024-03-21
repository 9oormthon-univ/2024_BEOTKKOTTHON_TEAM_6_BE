package org.goormthon.beotkkotthon.rebook.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "study_histories")
@DynamicUpdate
public class StudyHistory {
    /* -------------------------------------------- */
    /* -------------- Default Column -------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* -------------------------------------------- */
    /* ------------ Information Column ------------ */
    /* -------------------------------------------- */
    @Column(name = "image_url", nullable = false, updatable = false)
    private String imageUrl;

    @Column(name = "is_marking", nullable = false, updatable = false)
    private Boolean isMarking;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* -------------- Relation Column ------------- */
    /* -------------------------------------------- */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "recycle_category_id", nullable = false, updatable = false)
    private RecycleCategory recycleCategory;

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public StudyHistory(
            String imageUrl,
            User user,
            RecycleCategory recycleCategory
    ) {
        // Information Column
        this.imageUrl = imageUrl;
        this.isMarking = false;
        this.createdAt = LocalDateTime.now();

        // Relation Column
        this.user = user;
        this.recycleCategory = recycleCategory;
    }

    public void updateMarking(Boolean isMarking) {
        this.isMarking = isMarking;
    }
}
