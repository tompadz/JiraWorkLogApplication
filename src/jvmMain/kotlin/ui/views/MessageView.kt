package ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import consts.Colors

@Composable
fun MessageView(text:String) {
    Box(
        Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .background(
                    Colors.menuBackground,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = text,
                fontSize = 14.sp
            )
        }
    }
}