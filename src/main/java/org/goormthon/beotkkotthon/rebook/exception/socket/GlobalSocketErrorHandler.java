package org.goormthon.beotkkotthon.rebook.exception.socket;

import lombok.extern.slf4j.Slf4j;
import org.goormthon.beotkkotthon.rebook.dto.common.ExceptionDto;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Slf4j
//@Component
//public class GlobalSocketErrorHandler extends StompSubProtocolErrorHandler {
//
//    public GlobalSocketErrorHandler() {
//        super();
//    }
//
//    @Override
//    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable throwable)
//    {
//        log.error("SocketErrorHandler catch handleClientMessageProcessingError : {}", throwable.getMessage());
//
//        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
//
//        accessor.setMessage(throwable.getMessage());
//        accessor.setLeaveMutable(true);
//
//        return MessageBuilder.createMessage(
//                ErrorCode.INVALID_SOCKET_ERROR.getMessage().getBytes(StandardCharsets.UTF_8),
//                accessor.getMessageHeaders()
//        );
//    }
//}

@Component
public class GlobalSocketErrorHandler extends StompSubProtocolErrorHandler {

    public GlobalSocketErrorHandler() {
        super();
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]>clientMessage, Throwable ex)
    {
        ex.printStackTrace();
        Throwable exception = new MessageDeliveryException("abc");
        return handleUnauthorizedException(clientMessage, exception);
    }

    private Message<byte[]> handleUnauthorizedException(Message<byte[]> clientMessage, Throwable ex)
    {

        ExceptionDto exceptionDto = new ExceptionDto(ErrorCode.TOKEN_UNKNOWN_ERROR);

        return prepareErrorMessage(clientMessage, exceptionDto, String.valueOf(ErrorCode.TOKEN_UNKNOWN_ERROR.getCode()));

    }

    private Message<byte[]> prepareErrorMessage(Message<byte[]> clientMessage, ExceptionDto exceptionDto, String errorCode)
    {

        String message = exceptionDto.getMessage();

        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        accessor.setMessage(errorCode);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(message.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}
