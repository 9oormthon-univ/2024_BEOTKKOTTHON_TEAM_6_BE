package org.goormthon.beotkkotthon.rebook.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ERole {
    USER("USER", "ROLE_USER"),
    ADMIN("ADMIN", "ROLE_ADMIN");

    private final String name;
    private final String securityName;

    public static ERole fromName(String name) {
        return Arrays.stream(ERole.values()).
                filter(role -> role.getName().equals(name)).
                findFirst().
                orElseThrow(() -> new IllegalArgumentException("No such role: " + name));
    }
}
