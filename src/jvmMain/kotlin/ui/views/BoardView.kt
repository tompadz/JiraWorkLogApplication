package ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import data.models.BoardModel
import ui.components.AsyncImageBitmap
import ui.components.AsyncImageXmlVector
import ui.components.TagView

@Composable
fun BoardView(
    board: BoardModel,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 10.dp, horizontal = 16.dp),
    ) {
        AsyncImageXmlVector(
            url = board.location.avatarURI,
            shape = CircleShape,
            modifier = Modifier
                .size(26.dp)
        )
        Spacer(
            modifier = Modifier
                .width(10.dp)
        )
        Text(
            text = board.location.projectName
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        TagView(board.location.projectKey)
        Spacer(
            modifier = Modifier
                .width(7.dp)
        )
        Icon(
            Icons.Default.KeyboardArrowRight,
            "",
            modifier = Modifier
                .padding(start = 5.dp)
                .alpha(0.3f)
        )
    }
}