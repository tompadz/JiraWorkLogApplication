package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import consts.Colors

enum class TagViewStyle {
    DEFAULT,
    SMALL
}

@Composable
fun TagView(
    key:String,
    color: Color = Colors.field,
    textColor : Color = Color.Black,
    style : TagViewStyle = TagViewStyle.DEFAULT
) {
    val textSize = if (style == TagViewStyle.DEFAULT) 14.sp else 10.sp
    Box(
        modifier = Modifier
            .background(
                color,
                RoundedCornerShape(5.dp)
            )
            .padding(5.dp)
    ) {
        Text(
            text = key,
            color = textColor,
            fontSize = textSize
        )
    }
}