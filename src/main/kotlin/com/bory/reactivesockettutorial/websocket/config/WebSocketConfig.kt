package com.bory.reactivesockettutorial.websocket.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.Sinks

@Configuration
class WebSocketConfig {
    @Bean
    fun urlHandlerMapping(webSocketHandler: WebSocketHandler): SimpleUrlHandlerMapping =
        SimpleUrlHandlerMapping(mapOf("/ws/messages" to webSocketHandler), 1)

    @Bean
    fun webSocketHandlerAdapter(): WebSocketHandlerAdapter = WebSocketHandlerAdapter()

    @Bean
    fun webSocketSinks() = Sinks.many().multicast().directBestEffort<String>()
}