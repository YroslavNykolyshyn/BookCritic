package com.bc.home.book_in_progress.finish

import com.bc.model.repository.BookData

data class FinishBookUIState(
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val book: BookData? = null,
    val rating: Float = 0f,
    val summary: String = "",
    val note: String = "",
)