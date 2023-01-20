package ui.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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

@Composable
fun ErrorDialog(
    target:Boolean,
    throwable: Throwable?,
    showOkButton: Boolean = false,
    onOkClick:() -> Unit = {}
) {

    val throwableText = throwable?.message?:throwable?.localizedMessage?:"unknown error"

    AnimatedVisibility(
        visible =  target,
        enter = fadeIn(animationSpec = tween(350)),
        exit = fadeOut(animationSpec = tween(350))
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
            Column(
                modifier = Modifier
                    .padding(40.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(5.dp)
                    )
                    .padding(10.dp)
                    .align(Alignment.Center)
            ) {
                Text(
                    text = "Error !",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = throwableText,
                )
                if (showOkButton) {
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = {
                            onOkClick()
                        },
                        content = {
                            Text(
                                text = "Ok"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(350f)

                    )
                }
            }
        }
    }
}