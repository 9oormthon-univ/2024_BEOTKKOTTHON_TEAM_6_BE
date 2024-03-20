package org.goormthon.beotkkotthon.rebook.intercepter.pre;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.goormthon.beotkkotthon.rebook.constant.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpUserIdInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        request.setAttribute(Constants.USER_ID_ATTRIBUTE_NAME, authentication.getName());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
