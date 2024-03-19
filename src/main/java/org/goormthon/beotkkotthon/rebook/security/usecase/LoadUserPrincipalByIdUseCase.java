package org.goormthon.beotkkotthon.rebook.security.usecase;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

@UseCase
public interface LoadUserPrincipalByIdUseCase {

    UserDetails execute(UUID userId);
}
