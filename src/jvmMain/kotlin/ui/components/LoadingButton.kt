package ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoadingButton(
    target: Boolean,
    text:String,
    onClick : () -> Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color = MaterialTheme.colors.primary,
    textColor:Color = MaterialTheme.colors.onPrimary
) {

    val shape = if (!target) RoundedCornerShape(5.dp) else CircleShape

    Box(
        modifier = modifier
            .background(
                color = buttonColor,
                shape = shape
            )
            .clickable {
                if (! target) {
                    onClick()
                }
            }
            .padding(10.dp)
    ) {
        AnimatedContent(targetState = target) {
            if (it) {
                CircularProgressIndicator(
                    strokeWidth = 3.dp,
                    color = textColor,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Center)
                )
            }else {
                Text(
                    text = text,
                    color = textColor
                )
            }
        }
    }
}