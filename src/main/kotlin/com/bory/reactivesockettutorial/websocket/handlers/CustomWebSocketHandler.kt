package com.bory.reactivesockettutorial.websocket.handlers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks

private val LOGGER: Logger = LoggerFactory.getLogger(CustomWebSocketHandler::class.java)


@Component
class CustomWebSocketHandler(
    @Qualifier("webSocketSinks") private val sinks: Sinks.Many<String>
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        return session.send(
            Flux.merge(
                messageFromRestApi(),
                messageFromWebSocket(session)
            )
                .map(session::textMessage)
        )
    }

    private fun messageFromRestApi() = sinks.asFlux()
        .doOnNext { LOGGER.debug("Sending In Sink Message: $it") }

    private fun messageFromWebSocket(session: WebSocketSession) =
        session.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .doOnNext { message ->
                LOGGER.debug("MESSAGE RECEIVED: $message")
            }
            .map { message -> "MESSAGE FROM WebSocket ::: $message" }
}
