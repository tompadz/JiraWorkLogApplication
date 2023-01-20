import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import consts.Theme
import moe.tlaster.precompose.PreComposeWindow
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import screens.auth.AuthorizationScreen
import screens.boards.BoardsScreen
import screens.splash.SplashScreen
import screens.sprints.SprintScreen
import screens.user.UserScreen
import ui.SlideNavTransition
import utils.AppSettings
import java.awt.Dimension

@Composable
@Preview
fun App() {
    val navigator = rememberNavigator()
    MaterialTheme(
        colors = Theme.colors()
    ) {
        NavHost(
            navigator = navigator,
            navTransition = SlideNavTransition(),
            initialRoute = ScreenRouts.SPLASH.route,
        ) {
            scene(
                route = ScreenRouts.SPLASH.route,
            ) {
                SplashScreen(navigator)
            }
            scene(
                route = ScreenRouts.AUTH.route,
            ) {
                AuthorizationScreen(navigator)
            }
            scene(
                route = ScreenRouts.BOARDS.route,
            ) {
                BoardsScreen(navigator)
            }
            scene(
                route = ScreenRouts.USER.route,
                navTransition = NavTransition()
            ) {
                UserScreen(navigator)
            }
            scene(
                route = ScreenRouts.SPLINTS.route + "/{id}"
            ) {
                val id: Int? = it.path<Int>("id")
                val title: String? = it.query<String>("title")
                SprintScreen(
                    navigator,
                    id !!,
                    title !!
                )
            }
        }
    }
}

enum class ScreenRouts(val route:String) {
    SPLASH("/splash"),
    AUTH("/auth"),
    BOARDS("/boards"),
    USER("/user"),
    SPLINTS("/sprints"),
}

fun main() = application {
    AppSettings.initSettings()
    PreComposeWindow(
        onCloseRequest = ::exitApplication,
        title = "Jira",
        state = rememberWindowState(width = 800.dp, height = 600.dp)
    ){
        window.minimumSize = Dimension(800, 600)
        App()
    }
}
