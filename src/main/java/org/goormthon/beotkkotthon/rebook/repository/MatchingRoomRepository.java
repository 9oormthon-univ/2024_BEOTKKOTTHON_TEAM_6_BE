package org.goormthon.beotkkotthon.rebook.repository;

import org.goormthon.beotkkotthon.rebook.dto.socket.MatchingRoom;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchingRoomRepository {
    public Optional<MatchingRoom> findById(String id);

    public MatchingRoom save(MatchingRoom matchingRoom);
}
