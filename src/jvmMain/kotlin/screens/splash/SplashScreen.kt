package screens.splash

import ScreenRouts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.compose.viewModel

@Composable
fun SplashScreen(
    navigator: Navigator
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel { SplashScreenViewModel() }
    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {
        SplashScreenViewModel.SplashState.CheckAuth -> {
            coroutineScope.launch {
                viewModel.getSelf()
            }
        }
        SplashScreenViewModel.SplashState.NeedAuth -> {
            navigator.navigate(ScreenRouts.AUTH.route)
        }
        SplashScreenViewModel.SplashState.Login -> {
            navigator.navigate(ScreenRouts.BOARDS.route)
        }
        is SplashScreenViewModel.SplashState.Error -> {
            //todo add error view
            println((uiState.value as SplashScreenViewModel.SplashState.Error).t.localizedMessage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Loading...",
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}