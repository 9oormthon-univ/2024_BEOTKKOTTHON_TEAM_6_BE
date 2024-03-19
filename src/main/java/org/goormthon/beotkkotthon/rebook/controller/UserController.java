package org.goormthon.beotkkotthon.rebook.controller;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.annotation.UserId;
import org.goormthon.beotkkotthon.rebook.dto.common.ResponseDto;
import org.goormthon.beotkkotthon.rebook.usecase.user.ReadUserUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final ReadUserUseCase readUserUseCase;

    @GetMapping("")
    public ResponseDto<?> readUser(
            @UserId UUID userId
            ) {
        return ResponseDto.ok(readUserUseCase.executeMono(userId));
    }
}
