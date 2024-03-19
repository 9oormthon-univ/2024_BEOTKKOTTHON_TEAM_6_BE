package org.goormthon.beotkkotthon.rebook.service.oauth;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.User;
import org.goormthon.beotkkotthon.rebook.dto.response.JwtTokenDto;
import org.goormthon.beotkkotthon.rebook.dto.type.EProvider;
import org.goormthon.beotkkotthon.rebook.dto.type.ERole;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.usecase.oauth.LoginByKakaoUseCase;
import org.goormthon.beotkkotthon.rebook.utility.JwtUtil;
import org.goormthon.beotkkotthon.rebook.utility.OAuth2Util;
import org.goormthon.beotkkotthon.rebook.utility.PasswordUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginByKakaoService implements LoginByKakaoUseCase {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final OAuth2Util oAuth2Util;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public JwtTokenDto execute(String accessToken) {
        Map<String, String> userInfo = oAuth2Util.getKakaoUserInformation(accessToken);

        String serialId = userInfo.get("id");

        UserRepository.UserSecurityForm userSecurityForm = userRepository.findFormBySerialIdAndProvider(serialId, EProvider.KAKAO)
                .orElseGet(() -> {
                    User user = userRepository.save(
                            User.builder()
                                    .serialId(serialId)
                                    .provider(EProvider.KAKAO)
                                    .role(ERole.USER)
                                    .nickname(userInfo.get("nickname"))
                                    .password(bCryptPasswordEncoder.encode(PasswordUtil.generateRandomPassword())).build()
                    );

                    return UserRepository.UserSecurityForm.of(user);
                });

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(
                userSecurityForm.getId(),
                userSecurityForm.getRole()
        );

        userRepository.updateRefreshToken(userSecurityForm.getId(), jwtTokenDto.getRefreshToken());

        return jwtTokenDto;
    }
}
