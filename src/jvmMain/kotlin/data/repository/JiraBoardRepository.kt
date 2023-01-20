package data.repository

import data.api.JiraBoardApi
import data.models.BasePagingResponse
import data.models.BoardModel
import utils.ResponseResult

object JiraBoardRepository : BaseRepository() {

    private val api = JiraBoardApi()

    suspend fun getAllBoards() : ResponseResult<BasePagingResponse<BoardModel>> =
        fetchPagingData {
            api.getAllBoards()
        }

}