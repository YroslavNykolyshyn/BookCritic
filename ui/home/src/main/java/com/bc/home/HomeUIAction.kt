package com.bc.home

sealed interface HomeUIAction {
    data object OnAddBookInProgress : HomeUIAction
    data class OnUpdateBookInProgress(val id: String) : HomeUIAction
}