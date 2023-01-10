package com.bory.reactivesockettutorial.rsocket.controllers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

private val LOGGER: Logger = LoggerFactory.getLogger(RSocketClientController::class.java)

@RestController
class RSocketClientController(
    private val rSocketRequester: RSocketRequester
) {
    @GetMapping(
        value = ["/rsocket/client/uuid/{stringType}"],
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE]
    )
    fun getUuid(@PathVariable("stringType") stringType: String): Flux<String> {
        LOGGER.debug("UUID Request ::: $stringType")

        return rSocketRequester.route("/rsocket/server/uuid")
            .data(stringType)
            .retrieveFlux(String::class.java)
    }
}