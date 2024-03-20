package org.goormthon.beotkkotthon.rebook.dto.socket;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.goormthon.beotkkotthon.rebook.annotation.Enum;
import org.goormthon.beotkkotthon.rebook.dto.common.SelfValidating;
import org.goormthon.beotkkotthon.rebook.dto.type.EMessage;
import org.springframework.lang.Nullable;

@Getter
public class MatchingMessageDto extends SelfValidating<MatchingMessageDto> {
    @NotNull
    @Enum(enumClass = EMessage.class)
    private final EMessage messageType;

    @NotNull
    private final String sender;

    @NotNull
    private final Boolean isSystem;

    protected MatchingMessageDto() {
        this.messageType = null;
        this.sender = null;
        this.isSystem = null;
    }

    @Builder
    public MatchingMessageDto(EMessage messageType, String sender, Boolean isSystem) {
        this.messageType = messageType;
        this.sender = sender;
        this.isSystem = isSystem;
        this.validateSelf();
    }

    public MatchingMessageDto sender(String sender) {
        return MatchingMessageDto.builder()
                .messageType(this.messageType)
                .sender(sender)
                .build();
    }

    @Override
    public String toString() {
        return "MatchingMessageDto{" +
                "messageType=" + messageType +
                ", sender='" + sender + '\'' +
                '}';
    }
}
