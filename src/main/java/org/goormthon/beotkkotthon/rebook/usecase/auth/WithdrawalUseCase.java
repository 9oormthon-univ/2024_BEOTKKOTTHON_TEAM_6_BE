package org.goormthon.beotkkotthon.rebook.usecase.auth;

import org.goormthon.beotkkotthon.rebook.annotation.UseCase;

import java.util.UUID;

@UseCase
public interface WithdrawalUseCase {
    void execute(UUID userId);
}
