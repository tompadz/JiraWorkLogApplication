package ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import consts.Colors
import data.models.IssueModel
import kotlinx.coroutines.launch
import screens.sprints.SprintScreenViewModel
import ui.components.AsyncImageXmlVector
import ui.components.TimerButton
import ui.dialogs.LoadingDialog

@Composable
fun IssuePageView(
    issueId: String?,
    modifier: Modifier,
    viewModel: SprintScreenViewModel,
) {

    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.issueUiState.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var error: Throwable? by remember { mutableStateOf(null) }
    var issue: IssueModel? by remember { mutableStateOf(null) }

    fun getIssue() {
        if (issueId == null) return
        coroutineScope.launch {
            viewModel.getIssue(issueId)
        }
    }

    when (uiState.value) {
        SprintScreenViewModel.IssueState.Loading -> {
            if (issueId != null) {
                isLoading = true
                error = null
            }
        }

        is SprintScreenViewModel.IssueState.Successes -> {
            isLoading = false
            issue = (uiState.value as SprintScreenViewModel.IssueState.Successes).issueModel
        }

        is SprintScreenViewModel.IssueState.Error -> {
            isLoading = false
            error = (uiState.value as SprintScreenViewModel.IssueState.Error).t
        }
    }

    LaunchedEffect(issueId) {
        if (viewModel.isPlaying) {
            viewModel.stop(
                onSuccesses = {

                },
                onError = {
                    println("error")
                }
            )
        }
        getIssue()
    }

    Box(
        modifier = modifier
            .defaultMinSize(400.dp)
    ) {
        if (issueId == null) {
            Box(
                Modifier.fillMaxSize()
            ) {
                IssueMessageView("Select issue")
            }
        } else {
            if (issue != null && error == null) {
                Column {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        IssueHeaderView(issue!!)
                        Spacer(Modifier.height(20.dp))
                        IssueTimeTracker(issue!!)
                        Spacer(Modifier.height(20.dp))
                        TimerButton(
                            seconds = viewModel.second,
                            minutes = viewModel.minute,
                            hours = viewModel.hours,
                            isPlaying = viewModel.isPlaying,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onStart = {
                                viewModel.start()
                            },
                            onStop = {
                                coroutineScope.launch {
                                    viewModel.stop(
                                        onSuccesses = {
                                            getIssue()
                                        },
                                        onError = {
                                            println("error")
                                        }
                                    )
                                }
                            },
                        )
                    }
                    Spacer(Modifier.height(20.dp))
                    Divider()
                    Spacer(Modifier.height(20.dp))
                    IssueLogHistory(issue!!)
                }
            } else if (error != null) {
                IssueMessageView("Error")
            }
        }
        LoadingDialog(isLoading)
    }
}

@Composable
fun IssueHeaderView(
    issue: IssueModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImageXmlVector(
            url = issue.fields.issuetype?.iconUrl ?: "",
            modifier = Modifier.size(16.dp),
            shape = RoundedCornerShape(3.dp)
        )
        Spacer(Modifier.width(5.dp))
        Text(
            text = issue.key,
            modifier = Modifier.alpha(0.7f)
        )
    }
    Spacer(Modifier.height(10.dp))
    Text(
        text = issue.fields.summary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun IssueTimeTracker(
    issue: IssueModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "time tracking",
            fontSize = 12.sp,
            modifier = Modifier.alpha(0.5f),
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(5.dp))
        LinearProgressIndicator(
            progress = issue.fields.progress.percent.toFloat() / 100,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(10.dp)
                ),
            color = Colors.tagActive,
            backgroundColor = Colors.gray
        )
        Spacer(Modifier.height(5.dp))
        Row {
            val time = issue.fields.timetracking
            val spend = time.timeSpent ?: "Time not marked yet"
            Text(
                text = "Spend: $spend",
                fontSize = 14.sp
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "Remaining: ${time.remainingEstimate}",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun IssueLogHistory(
    issue: IssueModel
) {
    Column {
        Text(
            text = "Issue update history:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(10.dp))
        if (issue.fields.worklog.worklogs.isNotEmpty()) {
            val worklog = issue.fields.worklog
            val isLastPage = worklog.total <= worklog.maxResults
            LazyColumn {
                itemsIndexed(worklog.worklogs) { index, it ->
                    WorkLogView(it)
                    if (index < worklog.worklogs.lastIndex) {
                        Divider(
                            thickness = 1.dp,
                            modifier = Modifier.padding(start = 52.dp)
                        )
                    }
                }
                if (!isLastPage) {
                    item {
                        IssueMessageView("And more")
                    }
                }
            }
        } else {
            IssueMessageView("Empty")
        }
    }
}