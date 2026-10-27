package com.bc.book.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.model.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState: StateFlow<AddBookUiState> = _uiState.asStateFlow()

    fun onAction(action: AddBookAction) {
        when (action) {
            AddBookAction.BackClicked -> {
                // Handle navigation in your navigation layer.
            }

            is AddBookAction.CoverSelected -> {
                update {
                    copy(coverUri = action.uri)
                }
            }

            AddBookAction.SaveClicked -> {
                saveBook()
            }

            is AddBookAction.TitleChanged -> update {
                copy(title = action.value)
            }

            is AddBookAction.AuthorChanged -> update {
                copy(author = action.value)
            }

            is AddBookAction.StatusChanged -> update {
                copy(status = action.value)
            }

            is AddBookAction.RatingChanged -> update {
                copy(rating = action.value)
            }

            is AddBookAction.StartedDateChanged -> update {
                copy(startedDate = action.value)
            }

            is AddBookAction.FinishedDateChanged -> update {
                copy(finishedDate = action.value)
            }

            is AddBookAction.SummaryChanged -> update {
                copy(summary = action.value)
            }

            is AddBookAction.NoteChanged -> update {
                copy(note = action.value)
            }
        }
    }

    private inline fun update(
        transform: AddBookUiState.() -> AddBookUiState,
    ) {
        _uiState.update { current ->
            current.transform()
        }
    }

    private fun saveBook() {
        val current = _uiState.value

        if (current.title.isBlank()) return

        viewModelScope.launch {
            // Map UI state to your Book domain model.
            // Call the repository to persist the book.
        }
    }
}
