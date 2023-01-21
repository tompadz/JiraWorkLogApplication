package screens.sprints

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.models.IssueModel
import data.models.SprintModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.compose.viewModel
import ui.components.Toolbar
import ui.dialogs.LoadingDialog
import ui.dialogs.TimerDialog
import ui.views.IssuePageView
import ui.views.SprintsListView

@Composable
fun SprintScreen(
    navigator: Navigator,
    id: Int,
    title: String
) {

    val viewModel = viewModel { SprintScreenViewModel() }
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.sprintUiState.collectAsState()

    var isLoading by remember { mutableStateOf(false) }
    var error: Throwable? by remember { mutableStateOf(null) }

    var sprints: List<SprintModel>? by remember { mutableStateOf(null) }
    var issues: Map<Int, List<IssueModel>?>? by remember { mutableMapOf() }

    var issueId: String? by remember { mutableStateOf(null) }

    fun getSprints() {
        coroutineScope.launch {
            sprints = null
            viewModel.getSprints(id)
        }
    }

    when (uiState.value) {
        SprintScreenViewModel.SprintsState.Loading -> {
            isLoading = true
            error = null
            getSprints()
        }

        is SprintScreenViewModel.SprintsState.Successes -> {
            val data = (uiState.value as SprintScreenViewModel.SprintsState.Successes)
            sprints = data.sprints
            issues = data.issues
            isLoading = false
        }

        is SprintScreenViewModel.SprintsState.Error -> {
            isLoading = false
            val throwable = (uiState.value as SprintScreenViewModel.SprintsState.Error).t
            error = throwable
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            Toolbar(
                title = title,
                navigator
            )
            Row {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (sprints != null) {
                        SprintsListView(
                            throwable = error,
                            sprints = sprints !!,
                            issues = issues,
                            modifier = Modifier,
                            viewModel = viewModel,
                            onIssueClick = { issueId = it.id }
                        )
                    }
                    LoadingDialog(isLoading)
                }
                Divider(
                    modifier = Modifier.fillMaxHeight().width(1.dp)
                )
                IssuePageView(
                    issueId = issueId,
                    modifier = Modifier.weight(1f),
                    viewModel = viewModel,
                )
            }
        }
    }
}