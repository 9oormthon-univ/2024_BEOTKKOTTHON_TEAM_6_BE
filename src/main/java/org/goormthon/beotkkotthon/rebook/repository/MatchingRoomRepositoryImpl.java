package org.goormthon.beotkkotthon.rebook.repository;

import jakarta.annotation.PostConstruct;
import org.goormthon.beotkkotthon.rebook.dto.socket.MatchingRoom;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MatchingRoomRepositoryImpl implements MatchingRoomRepository {
    private Map<String, MatchingRoom> matchingRooms;

    @PostConstruct
    public void init() {
        matchingRooms = new LinkedHashMap<>();
    }

    @Override
    public Optional<MatchingRoom> findById(String id) {
        return Optional.ofNullable(matchingRooms.get(id));
    }

    @Override
    public MatchingRoom save(MatchingRoom matchingRoom) {
        matchingRooms.put(matchingRoom.getRoomId(), matchingRoom);
        return matchingRoom;
    }
}
