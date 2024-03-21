package org.goormthon.beotkkotthon.rebook.dto.socket;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MatchingRoom{
    private final String roomId;

    private Integer userCount;

    @Builder
    public MatchingRoom(String roomId) {
        this.roomId = roomId;
        this.userCount = 0;
    }

    public void plusUserCount() {
        this.userCount++;
    }

    public void minusUserCount() {
        this.userCount--;
    }
}
