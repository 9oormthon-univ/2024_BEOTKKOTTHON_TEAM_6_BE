package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.domain.ChallengeRoomUser;
import org.goormthon.beotkkotthon.rebook.domain.RecycleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecycleCategoryRepository extends JpaRepository<RecycleCategory, Integer> {
    Optional<RecycleCategory> findByName(String name);
}
