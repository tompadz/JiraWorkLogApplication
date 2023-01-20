package data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import data.models.BoardModel
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import utils.ResponseResult

open class BaseRepository {
    suspend inline fun <reified T> fetchData(
        noinline request: suspend () -> HttpResponse,
    ): ResponseResult<T> {
        var result: ResponseResult<T>? = null
        flow<T> {
            val response = request.invoke()
            val body = response.bodyAsText()
            val jsonBody = Gson().fromJson(body, T::class.java)
            emit(jsonBody)
        }
            .flowOn(Dispatchers.IO)
            .catch { throwable ->
                println(throwable.message.toString())
                result = ResponseResult.Error(throwable)
            }.collect { data ->
                result = ResponseResult.Success(data)
            }
        return result!!
    }

    suspend inline fun <reified T> fetchPagingData(
        noinline request: suspend () -> HttpResponse,
    ): ResponseResult<T> {
        var result: ResponseResult<T>? = null
        flow {
            val response = request.invoke()
            val body = response.bodyAsText()
            val type = object : TypeToken<T>() {}.type
            val jsonBody: T = Gson().fromJson(body, type)
            emit(jsonBody)
        }
            .flowOn(Dispatchers.IO)
            .catch { throwable ->
                println(throwable.message.toString())
                result = ResponseResult.Error(throwable)
            }.collect { data ->
                result = ResponseResult.Success(data)
            }
        return result!!
    }
}