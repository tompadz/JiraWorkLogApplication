package ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimerValueView(
    seconds:String,
    minutes:String,
    hours:String,
    textColor:Color = Color.White,
    textSize: TextUnit = 14.sp
) {
    Row {
        val numberTransitionSpec : AnimatedContentScope<String>.() -> ContentTransform = {
            slideInVertically(initialOffsetY = {it}) + fadeIn() with slideOutVertically (targetOffsetY = {-it}) + fadeOut() using SizeTransform(false)
        }
        AnimatedContent(targetState = hours, transitionSpec = numberTransitionSpec) {
            Text(
                text = it,
                color = textColor,
                fontSize = textSize
            )
        }
        Text(
            text = ":",
            color = textColor,
            fontSize = textSize
        )
        AnimatedContent(targetState = minutes, transitionSpec = numberTransitionSpec) {
            Text(
                text = it,
                color = textColor,
                fontSize = textSize
            )
        }
        Text(
           text = ":",
            color = textColor,
            fontSize = textSize
        )
        AnimatedContent(targetState = seconds, transitionSpec = numberTransitionSpec) {
            Text(
                text = it,
                color = textColor,
                fontSize = textSize
            )
        }
    }
}