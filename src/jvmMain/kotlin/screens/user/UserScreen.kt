package screens.user

import ScreenRouts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.cli.Ktor
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import ui.components.AsyncImageBitmap
import ui.components.Toolbar
import ui.components.ToolbarMenuItem
import utils.AppSettings
import utils.AppUtil

@Composable
fun UserScreen(
    navigator: Navigator
) {

    val user = AppSettings.settings.user!!

    fun logout() {
        AppSettings.changeSettings(
            AppSettings.Settings(
                null,null,null
            )
        )
        navigator.navigate(
            route = ScreenRouts.SPLASH.route,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Toolbar(
                title = "Settings",
                navigator = navigator,
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImageBitmap(
                        url = user.avatarUrls?.fortyEight ?: "",
                        modifier = Modifier.size(50.dp),
                        shape = CircleShape
                    )
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = user.displayName ?: "Unknown Name",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.height(5.dp))
                        Text(
                            text = user.emailAddress,
                            fontSize = 12.sp,
                            modifier = Modifier.alpha(0.5f)
                        )
                    }
                }
            }
            Divider()
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        logout()
                    },
                    content = {
                        Text("Logout")
                    },
                )
            }
        }
    }
}