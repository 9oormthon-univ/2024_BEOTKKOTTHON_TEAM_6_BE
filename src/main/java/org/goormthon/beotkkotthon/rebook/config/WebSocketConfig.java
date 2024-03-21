package org.goormthon.beotkkotthon.rebook.config;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.exception.socket.GlobalSocketErrorHandler;
import org.goormthon.beotkkotthon.rebook.intercepter.pre.SocketUserIdArgumentResolver;
import org.goormthon.beotkkotthon.rebook.intercepter.pre.SocketUserIdInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.port}")
    private Integer rabbitPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    private final GlobalSocketErrorHandler globalSocketErrorHandler;
    private final SocketUserIdInterceptor socketUserIdInterceptor;
    private final SocketUserIdArgumentResolver socketUserIdArgumentResolver;

    @Bean
    public ServletServerContainerFactoryBean configureWebSocketContainer() {
        ServletServerContainerFactoryBean factory = new ServletServerContainerFactoryBean();
        factory.setMaxSessionIdleTimeout(TimeUnit.MINUTES.convert(30, TimeUnit.MILLISECONDS));
        factory.setAsyncSendTimeout(TimeUnit.SECONDS.convert(5, TimeUnit.MILLISECONDS));
        return factory;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config
                .setPathMatcher(new AntPathMatcher("."))
                .setApplicationDestinationPrefixes("/pub")

                .enableStompBrokerRelay("/exchange")
                .setRelayHost(rabbitHost)
                .setRelayPort(rabbitPort)
                .setSystemLogin(rabbitUsername)
                .setSystemPasscode(rabbitPassword)
                .setClientLogin(rabbitUsername)
                .setClientPasscode(rabbitPassword)
                .setVirtualHost("/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .setAllowedOrigins("*");

        registry.setErrorHandler(globalSocketErrorHandler);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(socketUserIdArgumentResolver);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(socketUserIdInterceptor);
    }
}
