package screens.boards

import data.models.BoardModel
import data.repository.JiraBoardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import utils.ResponseResult

class BoardsViewModel : ViewModel() {

    private val repository = JiraBoardRepository

    private var _boardsState: MutableStateFlow<List<BoardModel>?> = MutableStateFlow(null)

    private val _uiState = MutableStateFlow(
        if (_boardsState.value != null) {
            val result = uiState.value as BoardsState.Successes
            BoardsState.Successes(result.boards)
        } else {
            BoardsState.Loading
        }
    )

    val uiState: StateFlow<BoardsState> get() = _uiState.asStateFlow()


    suspend fun getBoards() {
        if (uiState.value != BoardsState.Loading) {
            _uiState.value = BoardsState.Loading
        }
        when (val result = repository.getAllBoards()) {
            is ResponseResult.Error -> {
                _boardsState.value = null
                _uiState.value = BoardsState.Error(result.error)
            }
            is ResponseResult.Success -> {
                _boardsState.value = result.value.values
                _uiState.value = BoardsState.Successes(result.value.values)
            }
        }
    }

    sealed class BoardsState {
        object Loading : BoardsState()
        data class Error(val t:Throwable) : BoardsState()
        data class Successes(val boards: List<BoardModel>) : BoardsState()
    }

}