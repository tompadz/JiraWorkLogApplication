package ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import consts.Colors
import data.models.IssueModel
import ui.components.AsyncImageXmlVector
import ui.components.TagView
import ui.components.TagViewStyle

@Composable
fun IssueView(
    issue: IssueModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImageXmlVector(
            url = issue.fields.issuetype?.iconUrl ?: "",
            modifier = Modifier
                .size(18.dp),
            shape = RoundedCornerShape(3.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = issue.fields.summary
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = issue.key,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.alpha(0.5f)
            )
        }
    }

}