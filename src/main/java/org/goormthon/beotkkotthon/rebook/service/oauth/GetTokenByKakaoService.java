package org.goormthon.beotkkotthon.rebook.service.oauth;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.usecase.oauth.GetTokenByKakaoUseCase;
import org.goormthon.beotkkotthon.rebook.utility.OAuth2Util;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTokenByKakaoService implements GetTokenByKakaoUseCase {
    private final OAuth2Util oAuth2Util;

    @Override
    public String execute(String authorizationCode) {
        return oAuth2Util.getKakaoAccessToken(authorizationCode);
    }
}
