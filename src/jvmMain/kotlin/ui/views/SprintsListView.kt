package ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.models.IssueModel
import data.models.SprintModel
import screens.sprints.SprintScreenViewModel
import ui.components.LoadingButton
import ui.dialogs.LoadingDialog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SprintsListView(
    throwable: Throwable?,
    sprints: List<SprintModel>,
    issues: Map<Int, List<IssueModel>?>?,
    modifier: Modifier,
    viewModel: SprintScreenViewModel,
    onIssueClick: (issue: IssueModel) -> Unit,
) {

    val itemIds by viewModel.itemIds.collectAsState()

    Box(modifier = modifier) {
        if (throwable == null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                sprints.forEachIndexed { index, sprint ->
                    stickyHeader(
                        key = sprint.id
                    ) {
                        Column {
                            if (index != 0) {
                                Divider()
                            }
                            SprintView(
                                sprint = sprint,
                                onSprintClick = { viewModel.onExpandClick(sprint.id) },
                                isExpanded = itemIds.contains(sprint.id)
                            )
                            if (index == sprints.size - 1) {
                                Divider()
                            }
                        }
                    }
                    item {
                        ExpandableIssueView(
                            issues?.get(sprint.id)!!,
                            itemIds.contains(sprint.id),
                            onIssueClick
                        )
                    }
                }
            }
        } else {
            MessageView("Error")
        }
    }
}