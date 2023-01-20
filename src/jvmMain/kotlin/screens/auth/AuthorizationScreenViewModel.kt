package screens.auth

import data.cli.Ktor
import data.repository.JiraUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import utils.AppSettings
import utils.CryptoUtil
import utils.ResponseResult

class AuthorizationScreenViewModel : ViewModel() {

    private val repository = JiraUserRepository

    private val _uiState = MutableStateFlow<AuthState>(AuthState.NeedAuth)
    val uiState: StateFlow<AuthState> get() = _uiState.asStateFlow()

    suspend fun auth(
        login:String,
        token:String,
        url:String
    ) {
        val inputText = "$login:$token"
        val encodeText = CryptoUtil().encodeToBase64(inputText)
        Ktor.token = encodeText
        Ktor.baseUrl = url
        getSelf()
    }

    fun cleanState() {
        _uiState.value = AuthState.NeedAuth
    }

    private suspend fun getSelf() {
        _uiState.value = AuthState.Loading
        when (val result = repository.getSelf()) {
            is ResponseResult.Error -> {
                Ktor.token = ""
                Ktor.baseUrl = ""
                AppSettings.changeSettings(
                    AppSettings.settings.copy(
                        token = null,
                        user = null ,
                        url = null
                    )
                )
                _uiState.value = AuthState.Error(result.error)
            }
            is ResponseResult.Success -> {
                AppSettings.changeSettings(
                    AppSettings.settings.copy(
                        token = Ktor.token,
                        user = result.value,
                        url = Ktor.baseUrl
                    )
                )
                _uiState.value = AuthState.Login
            }
        }
    }

    sealed class AuthState {
        object NeedAuth : AuthState()
        object Loading : AuthState()
        object Login : AuthState()
        data class Error(val t:Throwable) : AuthState()
    }

}