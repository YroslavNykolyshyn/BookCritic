package com.bc.home.book_in_progress.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bc.home.book_in_progress.BookInProgressViewModel

@Composable
fun BookInProgressRouter(
    viewModel: BookInProgressViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BookInProgressScreen(state = uiState, onAction = viewModel::onAction)
}