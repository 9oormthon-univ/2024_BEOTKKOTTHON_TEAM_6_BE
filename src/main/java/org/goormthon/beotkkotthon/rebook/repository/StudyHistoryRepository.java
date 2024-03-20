package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.domain.StudyHistory;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.type.EProvider;
import org.goormthon.beotkkotthon.rebook.dto.type.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudyHistoryRepository extends JpaRepository<StudyHistory, Integer> {
    @Query("select sh.id, sh.imageUrl, sh.isMarking from StudyHistory sh join fetch sh.recycleCategory where sh.recycleCategory.name = :category")
    Page<StudyHistory> findAllByRecycleCategoryName(@Param("category") String category, Pageable pageable);
}
