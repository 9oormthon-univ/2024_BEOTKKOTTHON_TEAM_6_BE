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
import org.goormthon.beotkkotthon.rebook.usecase.user.UpdateUserNotificationStatusUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserService implements ReadUserUseCase {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public UserDto execute(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        UserStatus userStatus = userStatusRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return UserDto.builder()
                .id(user.getId().toString())
                .nickname(user.getNickname())
                .code(user.getSerialId())
                .environmentalTemperature(String.format("%.1f", user.getEnvironmentalTemperature()))
                .isActiveNotification(userStatus.getIsEnableNotification())
                .notificationHour(userStatus.getNotificationTime().getHour())
                .notificationMinute(userStatus.getNotificationTime().getMinute())
                .build();
    }
}
