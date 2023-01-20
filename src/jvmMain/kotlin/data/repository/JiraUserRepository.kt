package data.repository

import data.api.JiraUserApi
import data.models.UserModel
import utils.ResponseResult

object JiraUserRepository : BaseRepository() {
    val api = JiraUserApi()
    suspend fun getSelf():ResponseResult<UserModel> {
        return fetchData {
            api.getSelf()
        }
    }
}