package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.domain.Challenge;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
    // 입력받은 Challenge Ids에 해당하는 Challenge들을 조회하는데
    // 유저가 참여한 챌린지들만 조회한다.
    @Query("SELECT c.id AS challengeId, cr.id AS challengeRoomId, cr.deadlineAt AS deadlineAt " +
            "FROM ChallengeRoom cr JOIN cr.challenge c JOIN cr.challengeRoomUsers cru " +
            "WHERE cru.user = :user " +
            "AND c.id IN :challengeIds")
    List<UserParticipateChallenge> findAllByUserParticipateInId(
            @Param("user") User user,
            List<Integer> challengeIds
    );

    interface UserParticipateChallenge {
        Integer getChallengeId();
        Integer getChallengeRoomId();
        LocalDateTime getDeadlineAt();
    }
}
