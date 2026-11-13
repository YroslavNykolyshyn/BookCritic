package com.bc.home.book_in_progress.finish

sealed interface FinishBookUIAction {
    data class OnRatingChanged(val rating: Float) : FinishBookUIAction
    data class OnSummaryChanged(val summary: String) : FinishBookUIAction
    data class OnNoteChanged(val note: String) : FinishBookUIAction
    data object OnConfirm : FinishBookUIAction
}