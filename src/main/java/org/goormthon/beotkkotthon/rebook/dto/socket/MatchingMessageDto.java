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
    private final String targetId;

    protected MatchingMessageDto() {
        this.messageType = null;
        this.targetId = null;
    }

    @Builder
    public MatchingMessageDto(EMessage messageType, String targetId) {
        this.messageType = messageType;
        this.targetId = targetId;
        this.validateSelf();
    }

    public MatchingMessageDto targetId(String targetId) {
        return MatchingMessageDto.builder()
                .messageType(this.messageType)
                .targetId(targetId)
                .build();
    }

    @Override
    public String toString() {
        return String.format(
                "[ MatchingMessage -> MessageType: %s, TargetId: %s ]",
                this.messageType,
                this.targetId
        );
    }
}
