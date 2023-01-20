package screens.boards

import ScreenRouts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.models.BoardModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.compose.viewModel
import ui.dialogs.ErrorDialog
import ui.dialogs.LoadingDialog
import ui.views.BoardView
import ui.views.BoardsToolbarView

@Composable
fun BoardsScreen(
    navigator: Navigator
) {

    val viewModel = viewModel { BoardsViewModel() }
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var error : Throwable? by remember { mutableStateOf(null) }
    var boards : List<BoardModel>? by remember { mutableStateOf(null) }

    fun getBoards() {
        coroutineScope.launch {
            boards = null
            viewModel.getBoards()
        }
    }

    when (uiState.value) {
        is BoardsViewModel.BoardsState.Error -> {
            isLoading = false
            val throwable = (uiState.value as BoardsViewModel.BoardsState.Error).t
            error = throwable
        }
        BoardsViewModel.BoardsState.Loading -> {
            isLoading = true
            error = null
            getBoards()
        }
        is BoardsViewModel.BoardsState.Successes -> {
            isLoading = false
            boards = (uiState.value as BoardsViewModel.BoardsState.Successes).boards
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            BoardsToolbarView {
                navigator.navigate(ScreenRouts.USER.route)
            }
            if (boards != null) {
                LazyColumn(
                    modifier = Modifier
                        .background(
                            Color.White
                        )
                ) {
                    itemsIndexed(boards !!) {  index, item ->
                        BoardView(
                            board = item,
                            onClick = {
                                val routeArgs = "/${item.id}?title=${item.location.displayName}"
                                navigator.navigate(ScreenRouts.SPLINTS.route + routeArgs)
                            }
                        )
                        if (index < boards !!.lastIndex) {
                            Divider(
                                thickness = 1.dp,
                                modifier = Modifier.padding(start = 52.dp)
                            )
                        }
                    }
                }
            }
        }
        LoadingDialog(isLoading)
        ErrorDialog(
            target = error != null,
            throwable = error,
            showOkButton = true,
            onOkClick = {
                getBoards()
            }
        )
    }
}