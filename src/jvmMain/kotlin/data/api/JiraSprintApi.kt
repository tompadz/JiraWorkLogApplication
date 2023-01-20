package data.api

import data.cli.Ktor
import io.ktor.client.request.*
import io.ktor.client.statement.*

class JiraSprintApi {
    suspend fun getAllSprints(boardId:Int): HttpResponse {
        return Ktor.client.get("rest/agile/1.0/board/$boardId/sprint?state=active,future")
    }
}