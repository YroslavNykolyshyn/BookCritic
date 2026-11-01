package com.bc.library.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.INavigation
import com.bc.model.repository.BookData
import com.bc.model.repository.IBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val bookRepository: IBookRepository,
    private val navigation: INavigation,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState: StateFlow<AddBookUiState> = _uiState.asStateFlow()

    init {
        observeReadBookCount()
    }

    fun onAction(action: AddBookUIAction) {
        when (action) {
            AddBookUIAction.BackClicked -> {
                navigation.goBack()
            }

            is AddBookUIAction.CoverSelected -> {
                onCoverSelected(action.uri)
            }

            AddBookUIAction.SaveClicked -> {
                    saveBook()
            }

            is AddBookUIAction.TitleChanged -> updateBookItem {
                copy(title = action.value)
            }

            is AddBookUIAction.AuthorChanged -> updateBookItem {
                copy(author = action.value)
            }

            is AddBookUIAction.StatusChanged -> updateBookItem {
                copy(status = action.value)
            }

            is AddBookUIAction.RatingChanged -> updateBookItem {
                copy(rating = action.value)
            }

            is AddBookUIAction.StartedDateChanged -> updateBookItem {
                copy(startedDate = action.value)
            }

            is AddBookUIAction.FinishedDateChanged -> updateBookItem {
                copy(finishedDate = action.value)
            }

            is AddBookUIAction.SummaryChanged -> updateBookItem {
                copy(summary = action.value)
            }

            is AddBookUIAction.NoteChanged -> updateBookItem {
                copy(note = action.value)
            }
        }
    }

    private fun saveBook() {
        val state = _uiState.value

        if (state.isSaving) return

        val item = state.bookItem
        val title = item.title.trim()

        if (title.isBlank()) return

        // Set the guard before the first suspension.
        _uiState.update { it.copy(isSaving = true) }

        try {
            val book = BookData(
                id = UUID.randomUUID().toString(),
                title = title,
                author = item.author.trim(),
                status = item.status,
                rating = item.rating,
                startedDate = item.startedDate,
                finishedDate = item.finishedDate,
                summary = item.summary.trim(),
                note = item.note.trim(),
                coverPath = null,
                bookCost = item.bookCost.trim().toLongOrNull(),
            )

            val selectedCoverUri = item.selectedCoverUri
                ?.let(Uri::parse)

            viewModelScope.launch {
                bookRepository.saveBook(
                    book = book,
                    selectedCoverUri = selectedCoverUri,
                )
            }
            navigation.goBack()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save book", e)
        } finally {
            _uiState.update { it.copy(isSaving = false) }
        }
    }

    private fun onCoverSelected(uri: Uri) {
        updateBookItem {
            copy(selectedCoverUri = uri.toString())
        }
    }

    private fun observeReadBookCount() {
        viewModelScope.launch {
            bookRepository.observeBooksCount().collect { count ->
                _uiState.update { state ->
                    state.copy(
                        readBookCount = count
                    )
                }
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

    private inline fun updateBookItem(
        transform: AddBookItem.() -> AddBookItem,
    ) {
        update {
            copy(bookItem = bookItem.transform())
        }
    }

    companion object {
        private const val TAG = "AddBookViewModel"
    }
}
