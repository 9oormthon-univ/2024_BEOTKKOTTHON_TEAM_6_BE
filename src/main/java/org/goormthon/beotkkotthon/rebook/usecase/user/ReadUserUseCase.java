package org.goormthon.beotkkotthon.rebook.usecase.user;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;
import org.goormthon.beotkkotthon.rebook.dto.response.user.UserDto;

import java.util.UUID;

@UseCase
public interface ReadUserUseCase {
    UserDto execute(UUID userId);
}
