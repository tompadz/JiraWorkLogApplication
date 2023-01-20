package ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.models.WorklogModel
import ui.components.AsyncImageBitmap
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun convertDate(date:String) :String {
    val df2: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH)
    val date = df2.parse(date)
    val newDateFormat = SimpleDateFormat("DD MMMM yyyy | HH:mm")
    return newDateFormat.format(date)
}

@Composable
fun WorkLogView(
    worklogModel: WorklogModel
) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 16.dp)
    ) {
        AsyncImageBitmap(
            url =worklogModel.author.avatarUrls?.fortyEight?:"",
            modifier = Modifier.size(28.dp),
            shape = CircleShape
        )
        Spacer(Modifier.width(10.dp))
        Column {
            val username = worklogModel.author.displayName?:"Unknown"
            val spend = worklogModel.timeSpent
            Text(
                text = "$username registered $spend",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(5.dp))
            val date = convertDate(worklogModel.created)
            Text(
                text = date,
                fontSize = 12.sp,
                modifier = Modifier.alpha(0.5f)
            )
        }
    }
}