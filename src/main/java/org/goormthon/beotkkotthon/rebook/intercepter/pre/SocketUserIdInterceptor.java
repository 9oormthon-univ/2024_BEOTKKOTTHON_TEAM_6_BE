package org.goormthon.beotkkotthon.rebook.intercepter.pre;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.utility.HeaderUtil;
import org.goormthon.beotkkotthon.rebook.utility.JwtUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SocketUserIdInterceptor implements ChannelInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(
            Message<?> message,
            MessageChannel channel
    ) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getDestination() == null || StringUtils.startsWith(accessor.getDestination(), "/exchange")) {
            return message;
        }

        String token = HeaderUtil.refineHeader(accessor, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));
        Claims claims = jwtUtil.validateToken(token);

        UUID userId = UUID.fromString(claims.get(Constants.USER_ID_CLAIM_NAME, String.class));

        accessor.setNativeHeader(Constants.USER_ID_ATTRIBUTE_NAME, userId.toString());

        return message;
    }
}
