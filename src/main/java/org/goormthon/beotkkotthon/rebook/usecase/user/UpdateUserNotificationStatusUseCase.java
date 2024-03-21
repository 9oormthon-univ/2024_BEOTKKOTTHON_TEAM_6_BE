package org.goormthon.beotkkotthon.rebook.usecase.user;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.request.UserNotificationRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserNotificationStatusUseCase {
    Void execute(UUID userId, UserNotificationRequestDto userNotificationRequestDto);
}
