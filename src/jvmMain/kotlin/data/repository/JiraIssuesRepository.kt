package data.repository

import data.api.JiraIssuesApi
import data.models.BasePagingResponse
import data.models.IssueModel
import data.models.WorklogModel
import utils.ResponseResult

object JiraIssuesRepository : BaseRepository() {

    private val api = JiraIssuesApi()

    suspend fun getAllIssues(boardId:Int, sprintId:Int, userId:String) : ResponseResult<BasePagingResponse<IssueModel>> =
        fetchPagingData {
            api.getAllIssues(boardId, sprintId, userId)
        }

    suspend fun getIssues(issueId:String) : ResponseResult<IssueModel> =
        fetchPagingData {
            api.getIssues(issueId)
        }

    suspend fun setNewTime(issueKey:String, time:Long) : ResponseResult<WorklogModel> =
        fetchData {
            api.setNewTime(issueKey, time)
        }

}