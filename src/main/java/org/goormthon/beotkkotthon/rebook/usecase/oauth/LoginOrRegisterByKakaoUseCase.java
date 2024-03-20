package org.goormthon.beotkkotthon.rebook.usecase.oauth;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.JwtTokenDto;

@UseCase
public interface LoginOrRegisterByKakaoUseCase {
    JwtTokenDto execute(String accessToken);
}
