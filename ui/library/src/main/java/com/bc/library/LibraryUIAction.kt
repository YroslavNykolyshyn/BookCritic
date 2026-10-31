package com.bc.library

sealed interface LibraryUIAction {
    data class OnSearchChanged(val query: String) : LibraryUIAction
    data object OnFilterClicked : LibraryUIAction
    data object OnAddBookClicked : LibraryUIAction
    data class OnEditBookClicked(val id: String) : LibraryUIAction
}