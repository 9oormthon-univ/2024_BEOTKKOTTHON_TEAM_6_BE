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
import org.goormthon.beotkkotthon.rebook.usecase.user.UpdateUserNotificationTimeUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.user.UpdateUserNotificationUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements ReadUserUseCase, UpdateUserNotificationUseCase, UpdateUserNotificationTimeUseCase {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public UserDto execute(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        UserStatus userStatus = userStatusRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return UserDto.builder()
                .nickname(user.getNickname())
                .code(user.getSerialId())
                .environmentalTemperature(String.format("%.1f", user.getEnvironmentalTemperature()))
                .isActiveNotification(userStatus.getIsEnableNotification())
                .notificationHour(userStatus.getNotificationTime().getHour())
                .notificationMinute(userStatus.getNotificationTime().getMinute())
                .build();
    }

    @Transactional
    @Override
    public Void execute(UUID userId, UserNotificationRequestDto userNotificationRequestDto) {
        UserStatus userStatus = userStatusRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        userStatus.updateEnableNotification(userNotificationRequestDto.isActiveNotification());

        return null;
    }

    @Transactional
    @Override
    public Void execute(UUID userId, UserNotificationTimeRequestDto userNotificationTimeRequestDto) {
        UserStatus userStatus = userStatusRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        userStatus.updateNotificationTime(Integer.parseInt(userNotificationTimeRequestDto.notificationHour()), Integer.parseInt(userNotificationTimeRequestDto.notificationMinute()));

        return null;
    }
}
