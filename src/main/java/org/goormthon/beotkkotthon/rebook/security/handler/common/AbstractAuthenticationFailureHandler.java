package org.goormthon.beotkkotthon.rebook.security.handler.common;

import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;
import org.goormthon.beotkkotthon.rebook.dto.common.ExceptionDto;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AbstractAuthenticationFailureHandler {
    protected void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", null);
        result.put("error", ExceptionDto.of(errorCode));

        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
