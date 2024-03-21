package org.goormthon.beotkkotthon.rebook.constant;

import java.util.List;

public class Constants {
    public static String USER_ID_ATTRIBUTE_NAME = "USER_ID";
    public static String USER_ID_CLAIM_NAME = "uid";
    public static String USER_ROLE_CLAIM_NAME = "rol";
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static final String CHALLENGE_MATCHING_QUEUE_NAME = "challenge.matching.queue";
    public static final String CHALLENGE_MATCHING_EXCHANGE_NAME = "challenge.matching.exchange";
    public static final String CHALLENGE_MATCHING_ROUTING_KEY = "rooms.*";

    public static List<String> NO_NEED_AUTH_URLS = List.of(
            "/auth/login",
            "/auth/register",
            "/auth/reissue",
            "/oauth/login/kakao",
            "/oauth/login/kakao/callback",

            "/temp",

            "/ws-stomp",
            "/pub/**",
            "/exchange/**",

            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v3/**"
    );

    public static List<String> ANONYMOUS_URLS = List.of(
            "/api-docs.html",
            "/api-docs",
            "/swagger-ui",
            "/v3",

            "/temp",

            "/ws-stomp",
            "/pub",
            "/exchange"
    );

    public static List<String> USER_URLS = List.of(
            "/**");

    public static List<String> ADMIN_URLS = List.of(
            "/admin/**");
}
