package com.bc.home.book_in_progress

import android.net.Uri

sealed interface BookInProgressUIAction {
    data object OnBackClicked : BookInProgressUIAction

    data class OnCoverSelected(val uri: Uri) : BookInProgressUIAction
    data object OnSaveClicked : BookInProgressUIAction

    data class OnTitleChanged(val value: String) : BookInProgressUIAction
    data class OnAuthorChanged(val value: String) : BookInProgressUIAction
    data class OnPageChanged(val value: Int) : BookInProgressUIAction
}