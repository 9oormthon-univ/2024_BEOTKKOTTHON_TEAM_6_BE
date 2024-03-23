package org.goormthon.beotkkotthon.rebook.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.annotation.UserId;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.dto.request.RegisterDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.usecase.auth.RegisterUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.auth.ReissueTokenUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.auth.WithdrawalUseCase;
import org.goormthon.beotkkotthon.rebook.utility.HeaderUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Hidden
public class AuthController {
    private final ReissueTokenUseCase reissueTokenUseCase;
    private final WithdrawalUseCase withdrawalUseCase;
    private final RegisterUseCase registerUseCase;

    @PostMapping("/reissue")
    public ResponseDto<?> reissueToken(
            HttpServletRequest request
    ) {
        String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_AUTHORIZATION_HEADER));

        return ResponseDto.ok(reissueTokenUseCase.execute(refreshToken));
    }

    @PostMapping("/withdrawal")
    public ResponseDto<?> withdrawal(
            @UserId UUID userId
    ) {
        withdrawalUseCase.execute(userId);

        return ResponseDto.ok(null);
    }

    @Deprecated(since = "2021-09-01", forRemoval = true)
    @PostMapping("/register")
    public ResponseDto<?> register(
            @Valid @RequestBody RegisterDto registerDto
            ) {
        return ResponseDto.ok(registerUseCase.execute(registerDto));
    }
}
