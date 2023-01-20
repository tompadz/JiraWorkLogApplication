package screens.sprints

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.models.IssueModel
import data.models.SprintModel
import data.repository.JiraIssuesRepository
import data.repository.JiraSprintRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import utils.AppSettings
import utils.ResponseResult
import java.time.Duration
import java.util.*
import kotlin.concurrent.fixedRateTimer

class SprintScreenViewModel : ViewModel() {

    private val sprintRepository = JiraSprintRepository
    private val issueRepository = JiraIssuesRepository

    private var _sprintsState: MutableStateFlow<List<SprintModel>?> = MutableStateFlow(null)
    private var _issuesState: MutableStateFlow<Map<Int, List<IssueModel>?>?> = MutableStateFlow(null)

    private val itemIdsList = MutableStateFlow(listOf<Int>())
    val itemIds: StateFlow<List<Int>> get() = itemIdsList

    val sprintUiState: StateFlow<SprintsState> get() = _sprintUiState.asStateFlow()
    private val _sprintUiState = MutableStateFlow(
        if (_sprintsState.value != null) {
            val result = sprintUiState.value as SprintsState.Successes
            SprintsState.Successes(_sprintsState.value!!, _issuesState.value !!)
        } else {
            SprintsState.Loading
        }
    )

    val issueUiState : StateFlow<IssueState> get() = _issueUiState.asStateFlow()
    private val _issueUiState = MutableStateFlow<IssueState>(IssueState.Loading)

    private var time: Duration = Duration.ZERO
    private lateinit var timer: Timer

    var second by mutableStateOf("00")
    var minute by mutableStateOf("00")
    var hours by mutableStateOf("00")
    var isPlaying by mutableStateOf(false)

    fun start() {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time = time.plus(Duration.ofSeconds(1))
            updateTimeStates()
        }
        isPlaying = true
    }

    suspend fun stop(
        onSuccesses:() -> Unit,
        onError:() -> Unit
    ) {
        if (time.seconds >= 60) {
            setNewTime(time.seconds, onSuccesses, onError)
        }
        isPlaying = false
        timer.cancel()
        time = Duration.ZERO
        updateTimeStates()
    }

    private fun updateTimeStates() {
        second = time.toSecondsPart().pad()
        minute = time.toMinutesPart().pad()
        hours = time.toHoursPart().pad()
    }

    private fun Int.pad() : String {
        return this.toString().padStart(2, '0')
    }

    fun onExpandClick(id:Int) {
        itemIdsList.value = itemIdsList.value.toMutableList().also { list ->
            if (list.contains(id)) {
                list.remove(id)
            } else {
                list.add(id)
            }
        }
    }

    suspend fun getSprints(boardId: Int) {
        _sprintUiState.value = SprintsState.Loading
        when (val result = sprintRepository.getAllSprints(boardId)) {
            is ResponseResult.Error -> {
                _sprintsState.value = null
                _sprintUiState.value = SprintsState.Error(result.error)
            }

            is ResponseResult.Success -> {
                val sprints = result.value.values
                _sprintsState.value = sprints
                getIssuesFromSprints(sprints, boardId)
            }
        }
    }

    suspend fun getIssue(id:String) {
        _issueUiState.value = IssueState.Loading
        when (val result = issueRepository.getIssues(id)) {
            is ResponseResult.Error -> {
                _issueUiState.value = IssueState.Error(result.error)
            }
            is ResponseResult.Success -> {
                _issueUiState.value = IssueState.Successes(result.value)
            }
        }
    }

    private suspend fun setNewTime(
        time: Long,
        onSuccesses: () -> Unit,
        onError: () -> Unit,
    ) {
        val key = (_issueUiState.value as IssueState.Successes).issueModel.key
        when (issueRepository.setNewTime(key, time)) {
            is ResponseResult.Error -> {
                onError()
            }
            is ResponseResult.Success -> {
                onSuccesses()
            }
        }
    }

    private suspend fun getIssuesFromSprints(sprints: List<SprintModel>, boardId:Int) {
        val map = mutableMapOf<Int, List<IssueModel>?>()
        val userId = AppSettings.settings.user !!.accountId
        sprints.forEach {
            when(val result = issueRepository.getAllIssues(boardId, it.id, userId)) {
                is ResponseResult.Error -> {
                    map[it.id] = null
                }
                is ResponseResult.Success -> {
                    map[it.id] = result.value.issues
                }
            }
        }
        _issuesState.value = map
        _sprintUiState.value = SprintsState.Successes(_sprintsState.value !!, _issuesState.value !!)
    }

    sealed class SprintsState {
        object Loading : SprintsState()
        data class Error(val t: Throwable) : SprintsState()
        data class Successes(
            val sprints: List<SprintModel>,
            val issues: Map<Int, List<IssueModel>?>
        ) : SprintsState()
    }

    sealed class IssueState {
        object Loading: IssueState()
        data class Error(val t: Throwable) : IssueState()
        data class Successes(val issueModel: IssueModel) : IssueState()
    }
}