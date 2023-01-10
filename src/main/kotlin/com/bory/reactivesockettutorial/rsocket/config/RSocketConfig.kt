package com.bory.reactivesockettutorial.rsocket.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.util.MimeTypeUtils
import reactor.core.publisher.Sinks
import reactor.util.retry.Retry
import java.time.Duration

@Configuration
class RSocketConfig {
    @Bean
    fun rSocketSinks() = Sinks.many()
        .multicast()
        .directBestEffort<String>()

    @Bean
    fun rSocketRequester() = RSocketRequester.builder()
        .rsocketConnector { it.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))) }
        .dataMimeType(MimeTypeUtils.TEXT_PLAIN)
        .tcp("localhost", 7070)
}