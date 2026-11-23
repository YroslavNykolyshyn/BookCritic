package com.bc.home

import com.bc.model.repository.BookData

data class HomeUIState(
    val isLoading: Boolean = false,
    val bookInProgress: BookData? = null,
    val error: String? = null,
)