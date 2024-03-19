package org.goormthon.beotkkotthon.rebook.service.auth;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.dto.response.JwtTokenDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.auth.ReissueTokenUseCase;
import org.goormthon.beotkkotthon.rebook.utility.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReissueTokenService implements ReissueTokenUseCase {
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public JwtTokenDto execute(String refreshToken) {
        UUID userId;

        try {
            Claims claims = jwtUtil.validateToken(refreshToken);
            userId = UUID.fromString(claims.get(Constants.USER_ID_CLAIM_NAME, String.class));
        } catch (Exception e) {
            throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);
        }

        UserRepository.UserSecurityForm userSecurityForm = userRepository.findFormByIdAndRefreshToken(userId, refreshToken)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(
                userSecurityForm.getId(),
                userSecurityForm.getRole()
        );

        userRepository.updateRefreshToken(userSecurityForm.getId(), jwtTokenDto.getRefreshToken());

        return jwtTokenDto;
    }
}
