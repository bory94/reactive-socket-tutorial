package com.bory.reactivesockettutorial.rsocket.controllers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.util.*

private val LOGGER: Logger = LoggerFactory.getLogger(RSocketServerController::class.java)

/**
 * RSocket chatting tutorial
 * https://dev.to/olibroughton/building-a-scalable-live-stream-chat-service-with-spring-webflux-redis-pubsub-rsocket-and-auth0-22o9
 */
@Controller
class RSocketServerController(
    @Qualifier("rSocketSinks") private val rSocketSinks: Sinks.Many<String>
) {
    enum class StringType {
        LOWERCASE, UPPERCASE
    }

    @MessageMapping("/rsocket/server/uuid")
    fun getUuid(stringType: String): Flux<String> {
        LOGGER.debug("RSocket Client Accepted ::: $stringType")

        return rSocketSinks.asFlux().filter { str ->
            when (stringType) {
                StringType.LOWERCASE.name -> str[0].isLowerCase()
                StringType.UPPERCASE.name -> str[0].isUpperCase()
                else -> true
            }
        }
    }

    @Scheduled(fixedDelay = 333, initialDelay = 333)
    fun emitStringMessage() {
        val uuid = UUID.randomUUID().toString().let {
            if (Random().nextBoolean()) it.uppercase() else it.lowercase()
        }
        rSocketSinks.tryEmitNext(uuid)
    }
}