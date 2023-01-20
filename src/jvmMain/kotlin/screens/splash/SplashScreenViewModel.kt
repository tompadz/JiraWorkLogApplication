package screens.splash

import data.cli.Ktor
import data.repository.JiraUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import utils.AppSettings
import utils.ResponseResult

class SplashScreenViewModel : ViewModel() {

    private val repository = JiraUserRepository
    private val isAuth = AppSettings.settings.token != null
    private val startFlowState = if (isAuth) SplashState.CheckAuth else SplashState.NeedAuth
    private val _uiState = MutableStateFlow(startFlowState)
    val uiState: StateFlow<SplashState> get() = _uiState.asStateFlow()

    suspend fun getSelf() {
        Ktor.token = AppSettings.settings.token?:""
        Ktor.baseUrl = AppSettings.settings.url?:""
        when (val result = repository.getSelf()) {
            is ResponseResult.Error -> {
                Ktor.token = ""
                Ktor.baseUrl = ""
                AppSettings.changeSettings(
                    AppSettings.settings.copy(
                        token = null,
                        user = null,
                        url = null
                    )
                )
                _uiState.value = SplashState.Error(result.error)
            }
            is ResponseResult.Success -> {
                AppSettings.changeSettings(
                    AppSettings.settings.copy(
                        token = Ktor.token,
                        user = result.value,
                        url = Ktor.baseUrl
                    )
                )
                _uiState.value = SplashState.Login
            }
        }
    }

    sealed class SplashState {
        object CheckAuth : SplashState()
        object NeedAuth : SplashState()
        object Login : SplashState()
        data class Error(val t:Throwable) : SplashState()
    }
}