package ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.AsyncImageBitmap
import utils.AppSettings

@Composable
fun BoardsToolbarView(
    onUserClick: () -> Unit
) {

    val haveUserImage = AppSettings.settings.user?.avatarUrls?.twentyFour != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Boards",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            if (haveUserImage) {
                AsyncImageBitmap(
                    url = AppSettings.settings.user?.avatarUrls?.fortyEight?: "",
                    shape = CircleShape,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            onUserClick()
                        },
                    )
            }else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Open profile",
                    modifier = Modifier
                        .size(24.dp)
                        .alpha(0.5f)
                        .clickable {
                            onUserClick()
                        }
                )
            }
        }
        Divider()
    }
}