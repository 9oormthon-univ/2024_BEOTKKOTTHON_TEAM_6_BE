package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.domain.ChallengeRoom;
import org.goormthon.beotkkotthon.rebook.domain.ChallengeRoomStudyHistory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRoomStudyHistoryRepository extends JpaRepository<ChallengeRoomStudyHistory, Long> {

    @EntityGraph(attributePaths = {"studyHistory", "studyHistory.user", "studyHistory.recycleCategory"})
    List<ChallengeRoomStudyHistory> findAllByChallengeRoom(ChallengeRoom challengeRoom);

    Optional<ChallengeRoomStudyHistory> findByChallengeRoom(ChallengeRoom challengeRoom);
}
