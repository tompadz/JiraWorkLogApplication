package ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleTextField(
    text: String = "",
    hint: String = "",
    textViewModifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier,
    onTextChange: (text:String) -> Unit
) {

    var consumedText by remember { mutableStateOf(0) }
    var fieldText by remember { mutableStateOf(text) }

    BasicTextField(
        value = fieldText,
        onValueChange = {
            fieldText = it
            onTextChange(it)
        },
        modifier = textViewModifier
            .onPreviewKeyEvent {
                when {
                    (it.isCtrlPressed && it.key == Key.Minus && it.type == KeyEventType.KeyUp) -> {
                        consumedText -= fieldText.length
                        fieldText = ""
                        true
                    }
                    (it.isCtrlPressed && it.key == Key.Equals && it.type == KeyEventType.KeyUp) -> {
                        consumedText += fieldText.length
                        fieldText = ""
                        true
                    }
                    else -> false
                }
            },
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = boxModifier
                    .padding(vertical = 5.dp, horizontal = 13.dp)
                    .height(25.dp),
            ) {
                Box(Modifier.weight(1f)) {
                    if (fieldText.isEmpty()) Text(
                        text = hint,
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                            fontSize = 14.sp
                        )
                    )
                    innerTextField()
                }
            }
        }
    )
}