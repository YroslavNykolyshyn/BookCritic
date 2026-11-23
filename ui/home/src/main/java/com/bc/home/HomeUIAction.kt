package com.bc.home

sealed interface HomeUIAction {
    data object OnAddBookInProgress : HomeUIAction
    data class OnFinishBookInProgress(val id: String) : HomeUIAction
    data class OnCurrentPageChanged(val value: Long) : HomeUIAction
}