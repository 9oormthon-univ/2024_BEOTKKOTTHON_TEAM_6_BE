package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.domain.Challenge;
import org.goormthon.beotkkotthon.rebook.domain.ChallengeRoom;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ChallengeRoomRepository extends JpaRepository<ChallengeRoom, Integer> {

    @EntityGraph(attributePaths = {"challengeRoomUsers", "challengeRoomUsers.user", "challengeRoomUsers.user.userStatus"})
    Optional<ChallengeRoom> findById(Integer challengeRoomId);

    @Query("SELECT CASE WHEN COUNT(cru) > 0 THEN TRUE ELSE FALSE END " +
            "FROM ChallengeRoom cr JOIN cr.challengeRoomUsers cru " +
            "WHERE cru.user = :user AND cr.deadlineAt > :now")
    Boolean isUserParticipateInChallenge(
            @Param("user") User user,
            @Param("now") LocalDateTime now
    );
}
