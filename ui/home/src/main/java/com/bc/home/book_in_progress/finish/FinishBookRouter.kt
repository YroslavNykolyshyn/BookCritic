package com.bc.home.book_in_progress.finish

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bc.core.navigation.FinishBook

@Composable
fun FinishBookRouter(data: FinishBook) {
    val viewModel = hiltViewModel<FinishBookViewModel, FinishBookViewModel.Factory> { factory ->
        factory.create(data.bookId)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    FinishBookScreen(state = state, onAction = viewModel::onAction)
}