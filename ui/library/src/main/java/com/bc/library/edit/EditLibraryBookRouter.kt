package com.bc.library.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bc.core.navigation.EditLibraryBook
import com.bc.library.add.AddBookViewModel

@Composable
fun EditLibraryBookRouter(
    data: EditLibraryBook,
) {
    val viewModel = hiltViewModel<EditLibraryBookViewModel, EditLibraryBookViewModel.Factory> {factory ->
        factory.create(data.id)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    EditLibraryBookScreen(state = state, onAction = viewModel::onAction)
}