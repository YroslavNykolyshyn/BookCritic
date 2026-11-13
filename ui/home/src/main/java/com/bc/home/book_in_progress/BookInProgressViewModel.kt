package com.bc.home.book_in_progress

import androidx.lifecycle.ViewModel
import com.bc.home.book_in_progress.add.BookInProgressState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookInProgressViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(BookInProgressState())
    val uiState: StateFlow<BookInProgressState> = _uiState.asStateFlow()

    fun onAction(action: BookInProgressUIAction) {
        when(action) {
            is BookInProgressUIAction.OnAuthorChanged -> TODO()
            BookInProgressUIAction.OnBackClicked -> TODO()
            is BookInProgressUIAction.OnCoverSelected -> TODO()
            is BookInProgressUIAction.OnPageChanged -> TODO()
            BookInProgressUIAction.OnSaveClicked -> TODO()
            is BookInProgressUIAction.OnTitleChanged -> TODO()
        }
    }
}