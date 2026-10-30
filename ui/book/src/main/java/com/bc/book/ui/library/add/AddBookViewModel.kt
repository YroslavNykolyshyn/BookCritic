package com.bc.book.ui.library.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.INavigation
import com.bc.model.repository.BookData
import com.bc.model.repository.IBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
                clearState()
                navigation.goBack()
            }

            is AddBookUIAction.CoverSelected -> {
                saveCover(action.uri)
            }

            AddBookUIAction.SaveClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveBook()
                }
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
        val state = _uiState.value.bookItem

        val title = state.title.trim()
        if (title.isBlank()) return

        viewModelScope.launch {
            try {
                val book = BookData(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    author = state.author.trim(),
                    status = state.status,
                    rating = state.rating,
                    startedDate = state.startedDate,
                    finishedDate = state.finishedDate,
                    summary = state.summary.trim(),
                    note = state.note.trim(),
                    coverPath = state.coverUri,
                    bookCost = state.bookCost.trim().toLongOrNull(),
                )

                bookRepository.saveBook(book)
                clearState()
                navigation.goBack()
            } catch (e: Exception) {
                    Log.d(TAG, "saveBook: error message: $e")
            } finally {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun saveCover(uri: Uri) {
        viewModelScope.launch {
            runCatching {
                bookRepository.saveBookCover(uri)
            }.onSuccess { path ->
                updateBookItem {
                    copy(coverUri = path)
                }
            }
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

    private fun clearState() {
        _uiState.update {
            AddBookUiState()
        }
    }
    companion object {
        private const val TAG = "AddBookViewModel"
    }
}
