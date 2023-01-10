package com.bory.reactivesockettutorial.websocket.controllers

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Sinks

@RestController
class WebSocketController(
    @Qualifier("webSocketSinks") private val sinks: Sinks.Many<String>
) {
    @PostMapping("/websocket/{message}")
    fun onWebsocketMessage(@PathVariable("message") message: String) {
        sinks.tryEmitNext("MESSAGE FROM Rest API ::: $message")
    }
}