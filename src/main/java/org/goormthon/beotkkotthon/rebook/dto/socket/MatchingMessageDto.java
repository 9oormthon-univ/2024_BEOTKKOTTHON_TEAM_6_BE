package org.goormthon.beotkkotthon.rebook.dto.socket;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.goormthon.beotkkotthon.rebook.annotation.Enum;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;
import org.goormthon.beotkkotthon.rebook.dto.type.EMessage;
import org.springframework.lang.Nullable;

@Getter
public class MatchingMessageDto extends SelfValidating<MatchingMessageDto> {
    @NotNull
    @Enum(enumClass = EMessage.class)
    private final EMessage messageType;

    @Nullable
    private final String receiverId;

    @Nullable
    private final Integer challengeRoomId;

    protected MatchingMessageDto() {
        this.messageType = null;
        this.receiverId = null;
        this.challengeRoomId = null;
    }

    @Builder
    public MatchingMessageDto(EMessage messageType, String receiverId, Integer challengeRoomId) {
        this.messageType = messageType;
        this.receiverId = receiverId;
        this.challengeRoomId = challengeRoomId;
        this.validateSelf();
    }

    @Override
    public String toString() {
        return String.format(
                "[ MatchingMessage -> MessageType: %s, TargetId: %s, RoomId: %s ]",
                this.messageType,
                this.receiverId,
                this.challengeRoomId
        );
    }
}
