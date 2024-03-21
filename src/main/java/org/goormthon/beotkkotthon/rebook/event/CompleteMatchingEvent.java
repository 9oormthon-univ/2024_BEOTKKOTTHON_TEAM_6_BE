package org.goormthon.beotkkotthon.rebook.event;

import lombok.Builder;

import java.util.List;

@Builder
public record CompleteMatchingEvent(
        List<String> userIds,
        Integer challengeId,
        Integer roomId
) {
}
