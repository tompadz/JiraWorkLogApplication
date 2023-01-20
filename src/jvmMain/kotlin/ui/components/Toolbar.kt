package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.navigation.Navigator

data class ToolbarMenuItem(
    val label:String? = "",
    val icon:ImageVector = Icons.Default.Star,
    val onClick:() -> Unit
)

@Composable
fun Toolbar(
    title:String,
    navigator: Navigator,
    menuItems: List<ToolbarMenuItem> = listOf()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
        ) {
            if (navigator.canGoBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .clickable {
                            navigator.goBack()
                        }
                )
                Spacer(Modifier.width(16.dp))
            }
            Text(
                text = title,
                modifier = Modifier,
                fontSize = 16.sp
            )
            if (menuItems.isNotEmpty()) {
                Spacer(Modifier.width(16.dp))
                Spacer(Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    menuItems.forEachIndexed { index, it ->
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.label,
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                                .clickable { it.onClick() }
                        )
                        if (index != menuItems.size - 1) {
                            Spacer(Modifier.width(24.dp))
                        }
                    }
                }
            }
        }
        Divider()
    }
}