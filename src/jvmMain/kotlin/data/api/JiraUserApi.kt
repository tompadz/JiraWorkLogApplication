package data.api

import data.cli.Ktor
import io.ktor.client.request.*
import io.ktor.client.statement.*

class JiraUserApi {
    suspend fun getSelf() : HttpResponse {
        return Ktor.client.get("rest/api/2/myself")
    }
}