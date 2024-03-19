package org.goormthon.beotkkotthon.rebook.security.service;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.goormthon.beotkkotthon.rebook.security.info.UserPrincipal;
import org.goormthon.beotkkotthon.rebook.security.usecase.LoadUserPrincipalByIdUseCase;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceById implements UserDetailsService, LoadUserPrincipalByIdUseCase {
    private final UserRepository userRepository;

    /* TODO: 만약 사용할 시 구현 필요이 필요하지만 사용하지 않으므로 주석 처리함 */
    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }

    @Override
    public UserDetails execute(UUID userId) {
        UserRepository.UserSecurityForm userSecurityForm = userRepository.findFormById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return UserPrincipal.create(userSecurityForm);
    }
}
