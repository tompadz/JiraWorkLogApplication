package data.repository

import data.api.JiraSprintApi
import data.models.BasePagingResponse
import data.models.SprintModel
import utils.ResponseResult

object JiraSprintRepository : BaseRepository() {

    private val api = JiraSprintApi()

    suspend fun getAllSprints(boardId:Int) : ResponseResult<BasePagingResponse<SprintModel>> =
        fetchPagingData {
            api.getAllSprints(boardId)
        }

}