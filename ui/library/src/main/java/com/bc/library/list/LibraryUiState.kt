package com.bc.library.list

import com.bc.model.repository.BookGenre

data class LibraryUiState(
    val books: List<LibraryBookUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val searchQuery: String = "",
)

data class LibraryBookUiModel(
    val id: String,
    val title: String,
    val author: String,
    val genre: BookGenre?,
    val rating: Float?,
    val coverPath: String?,
    val bookSummary: String,
    val bookOptionalSummary: String,
    val isFavorite: Boolean = false,
)