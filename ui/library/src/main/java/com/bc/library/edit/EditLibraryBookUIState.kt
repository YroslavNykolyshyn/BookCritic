package com.bc.library.edit

data class EditLibraryBookUIState(
    val isLoading: Boolean = false,
    val bookItem: EditLibraryBookItem = EditLibraryBookItem(),
    val isEditing: Boolean = false,
)

data class EditLibraryBookItem(
    val title: String = "",
    val author: String = "",
    val rating: Float = 0f,
    val startedDate: Long? = null,
    val finishedDate: Long? = null,
    val summary: String = "",
    val note: String = "",
    val selectedCoverUri: String? = null,
    val bookCost: String = "",
    val bookCover: String? = null,
    val isFavorite: Boolean = false,
)