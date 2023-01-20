package screens.auth

import ScreenRouts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import consts.Colors
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.compose.viewModel
import ui.components.LoadingButton
import ui.components.SimpleTextField
import ui.dialogs.ErrorDialog
import utils.AppUtil

@Composable
fun AuthorizationScreen(
    navigator: Navigator
) {

    val creatTokenUrl = "https://id.atlassian.com/manage-profile/security/api-tokens"

    val viewModel = viewModel { AuthorizationScreenViewModel() }
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var url by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var throwable : Throwable? by remember { mutableStateOf(null) }

    when (uiState.value) {
        AuthorizationScreenViewModel.AuthState.NeedAuth -> {
            isLoading = false
            throwable = null
        }
        AuthorizationScreenViewModel.AuthState.Loading -> {
            isLoading = true
            throwable = null
        }
        AuthorizationScreenViewModel.AuthState.Login -> {
            isLoading = false
            navigator.navigate(ScreenRouts.BOARDS.route)
        }
        is AuthorizationScreenViewModel.AuthState.Error -> {
            isLoading = false
            val t = (uiState.value as AuthorizationScreenViewModel.AuthState.Error).t
            throwable = t
        }
    }

    fun auth() {
        coroutineScope.launch {
            viewModel.auth(login, token, url)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {

            Text(
                text = "Jira Work Log",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 15.dp),
                fontWeight = FontWeight.Medium
            )

            SimpleTextField(
                hint = "https://example.atlassian.net/",
                boxModifier = Modifier
                    .background(
                        Colors.field,
                        RoundedCornerShape(percent = 20)
                    ),
                onTextChange = {
                    url = it
                }
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            SimpleTextField(
                hint = "Login/Email",
                boxModifier = Modifier
                    .background(
                        Colors.field,
                        RoundedCornerShape(percent = 20)
                    ),
                onTextChange = {
                    login = it
                }
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            Row(
               verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleTextField(
                    hint = "Token",
                    textViewModifier =Modifier
                        .weight(1f),
                    boxModifier = Modifier
                        .background(
                            Colors.field,
                            RoundedCornerShape(percent = 20)
                        ),
                    onTextChange = {
                        token = it
                    }
                )
                Spacer(Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Click to create a new token",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .alpha(0.5f)
                        .clickable {
                            AppUtil().openInBrowser(creatTokenUrl)
                        }
                )
            }

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            LoadingButton(
                target = isLoading,
                text = "Login",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    auth()
                }
            )
        }

        ErrorDialog(
            target = throwable != null,
            throwable = throwable,
            showOkButton = true,
            onOkClick = {
                viewModel.cleanState()
            }
        )
    }
}