package com.example.demo.demos.config;

import com.example.demo.demos.web.chat.ChatWebSocketHandler;
import com.example.demo.demos.web.socket.MyBinaryWebSocketHandler;
import com.example.demo.demos.web.socket.MyTextWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyTextWebSocketHandler(), "/websocket-endpoint")
                .setAllowedOrigins("*"); // 允许的源可以根据需要进行配置
        registry.addHandler(new MyBinaryWebSocketHandler(), "/binary-websocket-endpoint")
            .setAllowedOrigins("*"); // 允许的源可以根据需要进行配置
        registry.addHandler(new ChatWebSocketHandler(), "/chat")
            .setAllowedOrigins("*"); // 允许的源可以根据需要进行配置
    }
}