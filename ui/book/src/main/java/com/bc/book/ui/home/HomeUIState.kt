package com.bc.book.ui.home

data class BookListUiState(
    val books: List<BookUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class BookUiModel(
    val id: String,
    val title: String,
    val authorsText: String? = null,
    val ratingText: String? = null,
    val coverPath: String? = null,
)