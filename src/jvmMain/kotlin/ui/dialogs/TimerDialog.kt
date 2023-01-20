package ui.dialogs

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screens.sprints.SprintScreenViewModel
import ui.components.TimerValueView

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimerDialog(
    viewModel: SprintScreenViewModel,
    target: Boolean,
    issueId: String,
    onDismiss: (issueId: String) -> Unit,
) {

    AnimatedVisibility(
        visible = target,
        enter = fadeIn(animationSpec = tween(200)) ,
        exit = fadeOut(animationSpec = tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.8f)
                    .background(
                        Color.Black
                    )
            )
            AnimatedVisibility(
                visible = target,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 200,
                        delayMillis = 200,
                    )
                ) + scaleIn(
                    animationSpec = tween(
                        delayMillis = 250
                    )
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        350
                    )
                ) + scaleOut()
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(200.dp, 100.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(10.dp)
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    TimerValueView(
                        seconds = viewModel.second,
                        minutes = viewModel.minute,
                        hours = viewModel.hours,
                        textColor = Color.DarkGray,
                        textSize = 20.sp
                    )
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = {
                            onDismiss(issueId)
                        },
                        content = {
                            Text(
                                text = "Stop"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                }
            }
        }
    }
}