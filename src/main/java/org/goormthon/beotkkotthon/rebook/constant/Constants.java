package org.goormthon.beotkkotthon.rebook.constant;

import java.util.List;

public class Constants {
    // JWT
    public static String USER_ID_ATTRIBUTE_NAME = "USER_ID";
    public static String USER_ID_CLAIM_NAME = "uid";
    public static String USER_ROLE_CLAIM_NAME = "rol";

    // HEADER
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";

    // RABBITMQ
    public static final String CHALLENGE_MATCHING_QUEUE_NAME = "matching.queue";
    public static final String CHALLENGE_MATCHING_EXCHANGE_NAME = "matching.exchange";
    public static final String CHALLENGE_MATCHING_ROUTING_KEY = "challenge.*.wait.room";

    // REDIS
    public static final String CHALLENGE_KEY = "challenge:";
    public static final Integer WAIT_TIMEOUT = 10000;
    public static final Integer LOCK_TIMEOUT = 2000;
    public static final Integer PLUS_TIMEOUT = 1000;

    public static List<String> NO_NEED_AUTH_URLS = List.of(
            "/auth/login",
            "/auth/register",
            "/auth/reissue",
            "/oauth/login/kakao",
            "/oauth/login/kakao/callback",

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

            "/ws-stomp",
            "/pub",
            "/exchange"
    );

    public static List<String> USER_URLS = List.of(
            "/**");

    public static List<String> ADMIN_URLS = List.of(
            "/admin/**");
}
