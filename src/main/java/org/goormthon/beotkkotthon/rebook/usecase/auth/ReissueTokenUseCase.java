package org.goormthon.beotkkotthon.rebook.usecase.auth;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.JwtTokenDto;

@UseCase
public interface ReissueTokenUseCase {
    JwtTokenDto execute(String refreshToken);
}
