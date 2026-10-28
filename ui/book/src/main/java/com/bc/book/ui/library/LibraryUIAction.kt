package com.bc.book.ui.library

sealed interface LibraryUIAction {
    data class SearchChanged(val query: String) : LibraryUIAction
    data object FilterClicked : LibraryUIAction
    data object AddBookClicked : LibraryUIAction
}