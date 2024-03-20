package org.goormthon.beotkkotthon.rebook.service.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goormthon.beotkkotthon.rebook.dto.socket.MatchingMessageDto;
import org.goormthon.beotkkotthon.rebook.dto.socket.MatchingRoom;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.MatchingRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingService {
    private final ObjectMapper objectMapper;

    private final MatchingRoomRepository matchingRoomRepository;

    public MatchingRoom readMatchingRoom(String roomId) {
        return matchingRoomRepository.findById(roomId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    public String createRoom(UUID userId) {
        String roomId = userId.toString();

        MatchingRoom matchingRoom = matchingRoomRepository.findById(userId.toString())
                .orElseGet(() -> matchingRoomRepository.save(MatchingRoom.builder().roomId(roomId).build()));

        return roomId;
    }

    public void sandMessage(WebSocketSession session, MatchingMessageDto matchingMessageDto) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(matchingMessageDto)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
