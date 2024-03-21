package org.goormthon.beotkkotthon.rebook.usecase.user;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.request.UserNotificationRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.request.UserNotificationTimeRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserNotificationTimeUseCase {
    Void execute(UUID userId, UserNotificationTimeRequestDto userNotificationTimeRequestDto);
}
