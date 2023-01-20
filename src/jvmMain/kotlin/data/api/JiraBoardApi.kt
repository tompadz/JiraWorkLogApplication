package data.api

import data.cli.Ktor
import io.ktor.client.request.*
import io.ktor.client.statement.*

class JiraBoardApi {
    suspend fun getAllBoards(): HttpResponse {
        return Ktor.client.get("rest/agile/1.0/board")
    }
}