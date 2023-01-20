package ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import data.models.IssueModel
import data.models.SprintModel
import screens.sprints.SprintScreenViewModel

@Composable
fun SprintsListView(
    throwable: Throwable?,
    sprints: List<SprintModel>,
    issues : Map<Int, List<IssueModel>?>?,
    modifier: Modifier,
    viewModel: SprintScreenViewModel,
    onIssueClick: (issue:IssueModel) -> Unit,
    ) {

    val itemIds by viewModel.itemIds.collectAsState()

    Box (modifier = modifier) {
        if (throwable == null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(sprints) { index, it ->
                    if (index != 0) {
                        Divider()
                    }
                    SprintView(
                        sprint = it,
                        sprintIssue = issues?.get(it.id),
                        isExpanded = itemIds.contains(it.id),
                        onSprintClick = { viewModel.onExpandClick(it.id) },
                        onIssueClick = { onIssueClick(it) }
                    )
                }
            }
        }else {
            Text("Error") //todo
        }
    }
}