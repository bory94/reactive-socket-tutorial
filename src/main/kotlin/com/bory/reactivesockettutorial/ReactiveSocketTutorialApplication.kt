package com.bory.reactivesockettutorial

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class ReactiveWebsocketTutorialApplication

fun main(args: Array<String>) {
    runApplication<ReactiveWebsocketTutorialApplication>(*args)
}
