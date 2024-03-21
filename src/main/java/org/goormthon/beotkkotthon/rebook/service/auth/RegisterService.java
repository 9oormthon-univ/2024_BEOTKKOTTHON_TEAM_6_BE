package org.goormthon.beotkkotthon.rebook.service.auth;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.domain.UserStatus;
import org.goormthon.beotkkotthon.rebook.dto.request.RegisterDto;
import org.goormthon.beotkkotthon.rebook.dto.response.JwtTokenDto;
import org.goormthon.beotkkotthon.rebook.dto.type.EProvider;
import org.goormthon.beotkkotthon.rebook.dto.type.ERole;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.repository.UserStatusRepository;
import org.goormthon.beotkkotthon.rebook.usecase.auth.RegisterUseCase;
import org.goormthon.beotkkotthon.rebook.utility.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterService implements RegisterUseCase {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public JwtTokenDto execute(RegisterDto registerDto) {
        userRepository.findBySerialId(registerDto.serialId())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
                });

        User user = userRepository.save(
                User.builder()
                        .serialId(registerDto.serialId())
                        .provider(EProvider.KAKAO)
                        .role(ERole.USER)
                        .nickname(registerDto.nickname())
                        .password(bCryptPasswordEncoder.encode(registerDto.password())).build()
        );

        UserStatus userStatus = userStatusRepository.save(
                UserStatus.builder()
                        .user(user).build()
        );

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(
                userStatus.getId(),
                user.getRole()
        );

        userRepository.updateRefreshToken(user.getId(), jwtTokenDto.getRefreshToken());

        return jwtTokenDto;
    }
}
