package com.bc.home.book_in_progress.finish

import com.bc.model.repository.BookGenre

sealed interface FinishBookUIAction {
    data class OnRatingChanged(val rating: Float) : FinishBookUIAction
    data class OnSummaryChanged(val summary: String) : FinishBookUIAction
    data class OnNoteChanged(val note: String) : FinishBookUIAction
    data object OnConfirm : FinishBookUIAction
    data object OnBackPressed: FinishBookUIAction

    data class OnGenreChanged(val genre: BookGenre?) : FinishBookUIAction
    data class OnCustomGenreAdded(val name: String) : FinishBookUIAction
}