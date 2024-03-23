package org.goormthon.beotkkotthon.rebook.security.info;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.dto.type.ERole;
import org.goormthon.beotkkotthon.rebook.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    @Getter private final UUID id;
    @Getter private final ERole role;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(UserRepository.UserSecurityForm form) {
        return UserPrincipal.builder()
                .id(form.getId())
                .role(form.getRole())
                .password(form.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(form.getRole().getSecurityName())))
                .build();
    }

    /* Common */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /* UserDetails */
    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
