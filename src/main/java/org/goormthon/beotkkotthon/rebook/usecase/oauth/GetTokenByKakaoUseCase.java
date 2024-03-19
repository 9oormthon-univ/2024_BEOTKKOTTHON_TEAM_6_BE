package org.goormthon.beotkkotthon.rebook.usecase.oauth;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;

@UseCase
public interface GetTokenByKakaoUseCase {
    String execute(String authorizationCode);
}
