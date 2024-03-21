package org.goormthon.beotkkotthon.rebook.exception.socket;

import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;

public class CommonSocketException extends Throwable {
    public CommonSocketException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
