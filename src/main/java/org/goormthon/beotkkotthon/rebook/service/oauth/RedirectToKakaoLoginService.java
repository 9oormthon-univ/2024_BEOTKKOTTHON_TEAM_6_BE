package org.goormthon.beotkkotthon.rebook.service.oauth;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.usecase.oauth.RedirectToKakaoLoginUseCase;
import org.goormthon.beotkkotthon.rebook.utility.OAuth2Util;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedirectToKakaoLoginService implements RedirectToKakaoLoginUseCase {
    private final OAuth2Util oAuth2Util;

    @Override
    public String execute() {
        return oAuth2Util.getKakaoRedirectUrl();
    }
}
