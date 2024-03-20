package org.goormthon.beotkkotthon.rebook.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.usecase.oauth.GetTokenByKakaoUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.oauth.LoginOrRegisterByKakaoUseCase;
import org.goormthon.beotkkotthon.rebook.usecase.oauth.RedirectToKakaoLoginUseCase;
import org.goormthon.beotkkotthon.rebook.utility.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Hidden
public class OAuthController {
    private final LoginOrRegisterByKakaoUseCase loginOrRegisterByKakaoUseCase;
    private final RedirectToKakaoLoginUseCase redirectToKakaoLoginUseCase;
    private final GetTokenByKakaoUseCase getTokenByKakaoUseCase;

    @PostMapping("/login/kakao")
    public ResponseDto<?> loginByKakao(
            HttpServletRequest request
    ) {
        String accessToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_AUTHORIZATION_HEADER));

        return ResponseDto.ok(loginOrRegisterByKakaoUseCase.execute(accessToken));
    }


    @GetMapping("/login/kakao")
    public ResponseEntity<?> loginByKakao() {
        // redirectToKakaoLoginUseCase로 리다이렉트 URL을 받아온다.
        String redirectUrl = redirectToKakaoLoginUseCase.execute();

        // ResponseEntity로 리다이렉트 URL을 반환한다.
        return ResponseEntity.status(302).header("Location", redirectUrl).build();
    }

    @GetMapping("/login/kakao/callback")
    public ResponseDto<String> loginByKakaoCallback(
            @RequestParam("code") String authorizationCode
    ) {
        return ResponseDto.ok(getTokenByKakaoUseCase.execute(authorizationCode));
    }
}
