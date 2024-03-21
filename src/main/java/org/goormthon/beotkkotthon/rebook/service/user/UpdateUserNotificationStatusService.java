package org.goormthon.beotkkotthon.rebook.service.user;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.domain.UserStatus;
import org.goormthon.beotkkotthon.rebook.dto.request.UserNotificationRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.request.UserNotificationTimeRequestDto;
import org.goormthon.beotkkotthon.rebook.dto.response.user.UserDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserStatusRepository;
import org.goormthon.beotkkotthon.rebook.usecase.user.ReadUserUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.user.UpdateUserNotificationStatusUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.user.UpdateUserNotificationTimeUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserNotificationStatusService implements UpdateUserNotificationStatusUseCase {
    private final UserStatusRepository userStatusRepository;

    @Transactional
    @Override
    public Void execute(UUID userId, UserNotificationRequestDto userNotificationRequestDto) {
        UserStatus userStatus = userStatusRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        userStatus.updateEnableNotification(userNotificationRequestDto.isActiveNotification());

        return null;
    }
}
