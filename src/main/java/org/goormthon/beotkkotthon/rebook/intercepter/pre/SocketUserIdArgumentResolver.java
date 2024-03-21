package org.goormthon.beotkkotthon.rebook.intercepter.pre;

import org.goormthon.beotkkotthon.rebook.annotation.UserId;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SocketUserIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UUID.class)
                && parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            Message<?> message
    ) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        String userId = accessor.getFirstNativeHeader(Constants.USER_ID_ATTRIBUTE_NAME);

        if (userId == null) {
            throw new CommonException(ErrorCode.INVALID_HEADER_ERROR);
        }



        return UUID.fromString(userId);
    }
}
