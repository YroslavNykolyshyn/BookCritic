package com.bc.book.ui.library

data class LibraryUiState(
    val searchQuery: String = "",
    val books: List<LibraryBookUiModel> = emptyList(),
)

data class LibraryBookUiModel(
    val id: String,
    val title: String,
    val author: String,
    val rating: Float?,
    val coverPath: String?,
    val bookSummary: String,
    val bookOptionalSummary: String,
)