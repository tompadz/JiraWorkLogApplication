package data.api

import com.google.gson.Gson
import data.cli.Ktor
import data.models.WorklogPostModel
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class JiraIssuesApi {
    suspend fun getAllIssues(boardId:Int, sprintId:Int, userId:String): HttpResponse {
        val fields = "timetracking,summary,issuetype,progress,worklog"
        return Ktor.client.get("rest/agile/1.0/board/$boardId/sprint/$sprintId/issue?fields=$fields&jql=assignee=$userId")
    }

    suspend fun getIssues(issueId:String): HttpResponse {
        val fields = "timetracking,summary,issuetype,progress,worklog"
        return Ktor.client.get("rest/api/3/issue/$issueId?fields=$fields")
    }

    suspend fun setNewTime(issueKey:String, time:Long): HttpResponse {
        val log = WorklogPostModel(time)
        val json = Gson().toJson(log)
        return Ktor.client.post {
            url("https://partnerkin.atlassian.net/rest/api/3/issue/$issueKey/worklog?")
            contentType(ContentType.Application.Json)
            setBody(json)
        }
    }
}