package org.goormthon.beotkkotthon.rebook.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record ImageAnalysisRequestDto(
        List<Object> reqeust
) {
}
