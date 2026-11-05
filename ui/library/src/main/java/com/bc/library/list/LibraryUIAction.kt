package com.bc.library.list

sealed interface LibraryUIAction {
    data class OnSearchChanged(val query: String) : LibraryUIAction
    data object OnFilterClicked : LibraryUIAction
    data object OnAddBookClicked : LibraryUIAction
    data class OnViewBookClicked(val id: String) : LibraryUIAction
    data class OnFavoriteBookClicked(val id: String) : LibraryUIAction
}