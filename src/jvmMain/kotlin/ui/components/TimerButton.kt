package ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import consts.Colors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimerButton(
    seconds:String,
    minutes:String,
    hours:String,
    isPlaying:Boolean,
    onStart:() -> Unit,
    onStop:() -> Unit,
    modifier: Modifier
) {

    val radius = if (!isPlaying) 5.dp  else 40.dp
    val cornerRadius = animateDpAsState(targetValue = radius)

    Box(
        modifier = modifier
            .clickable {
                if (isPlaying) {
                    onStop()
                }else {
                    onStart()
                }
            }
            .background(
                color = Colors.tagActive,
                shape = RoundedCornerShape(cornerRadius.value)
            )
            .padding(10.dp)
    ) {
        AnimatedContent(targetState = isPlaying) {
            if (it) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TimerValueView(seconds, minutes, hours)
                    Spacer(Modifier.width(10.dp))
                    Divider(
                        modifier = Modifier.height(20.dp).width(1.dp),
                        color = Color.White
                    )
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Filled.Pause,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }else {
                Text(
                    text = "Start Issue",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}