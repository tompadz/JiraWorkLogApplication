package ui.views

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import consts.Colors
import data.models.IssueModel
import data.models.SprintModel
import ui.components.TagView
import ui.components.TagViewStyle

@Composable
fun SprintView(
    sprint: SprintModel,
    sprintIssue: List<IssueModel>?,
    onSprintClick: () -> Unit,
    onIssueClick: (issue:IssueModel) -> Unit,
    isExpanded: Boolean
) {

    val isSprintActive = sprint.state == "active"

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onSprintClick()
                }
                .padding(vertical = 10.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                Icons.Default.ArrowDropDown,
                "",
                modifier = Modifier
                    .alpha(0.3f)
            )

            Spacer(
                modifier = Modifier
                    .width(7.dp)
            )
            Text(
                text = sprint.name
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
            TagView(
                key = sprint.state,
                color = if (isSprintActive) Colors.tagActive else Colors.field,
                textColor = if (isSprintActive) Color.White else Color.Black,
                style = TagViewStyle.SMALL
            )
        }
        if (isExpanded) {
            Divider()
        }
        if (sprintIssue != null) {
            ExpandableIssueView(
                sprintIssue,
                isExpanded,
                onIssueClick
            )
        }else {
            IssueMessageView("Error")
        }
    }
}

@Composable
fun IssueMessageView(text:String) {
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

@Composable
fun ExpandableIssueView(
    sprintIssue: List<IssueModel>,
    isExpanded: Boolean,
    onIssueClick: (issue:IssueModel) -> Unit
) {
    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }

    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition
    ) {

        if (sprintIssue.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                sprintIssue.forEachIndexed { index, issueModel ->
                    IssueView(
                        issue = issueModel,
                        onClick = {
                            onIssueClick(issueModel)
                        }
                    )
                    if (index < sprintIssue.lastIndex) {
                        Divider(
                            thickness = 1.dp,
                            modifier = Modifier.padding(start = 50.dp)
                        )
                    }
                }
            }
        }else {
            IssueMessageView("Sprint is empty")
        }
    }
}
