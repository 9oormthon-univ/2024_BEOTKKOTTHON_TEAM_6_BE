package org.goormthon.beotkkotthon.rebook.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EMessage {
    ENTER("ENTER"),
    LEAVE("LEAVE"),

    START_MATCHING("START_MATCHING"),
    COMPLETE_MATCHING("COMPLETE_MATCHING"),
    FAIL_MATCHING("FAIL_MATCHING"),

    ERROR("ERROR")
    ;

    private final String message;
}
