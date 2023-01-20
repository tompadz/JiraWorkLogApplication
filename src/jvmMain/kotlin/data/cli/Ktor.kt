package data.cli

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*

object Ktor {

    val client get() = ktor
    var token = ""
    var baseUrl = ""

    private val ktor = HttpClient(CIO) {
        install(WebSockets)
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        defaultRequest {
            url(baseUrl)
            header("Authorization", "Basic $token")
        }
    }
}