package org.goormthon.beotkkotthon.rebook.usecase.user;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.UserDetailDto;

import java.util.UUID;

@UseCase
public interface ReadUserUseCase {
    UserDetailDto executeMono(UUID userId);
}
