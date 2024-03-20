package org.goormthon.beotkkotthon.rebook.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EMessage {
    ENTER("ENTER"),
    LEAVE("LEAVE"),

    START("START"),
    COMPLETE("COMPLETE"),
    FAIL("FAIL"),
    ;

    private final String message;
}
