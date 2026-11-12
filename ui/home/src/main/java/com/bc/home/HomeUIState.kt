package com.bc.home

import com.bc.home.book_in_progress.add.BookInProgressState

data class HomeUIState(
    val isLoading: Boolean = false,
    val bookInProgressState: BookInProgressState? = null,
    val error: String? = null,
    val currentPage: Int = 0,
)