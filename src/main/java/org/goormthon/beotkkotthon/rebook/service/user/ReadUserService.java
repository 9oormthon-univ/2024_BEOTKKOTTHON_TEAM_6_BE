package org.goormthon.beotkkotthon.rebook.service.user;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.response.UserDetailDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.user.ReadUserUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserService implements ReadUserUseCase {
    private final UserRepository userRepository;

    @Override
    public UserDetailDto executeMono(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return UserDetailDto.builder()
                .nickname(user.getNickname()).build();
    }
}
