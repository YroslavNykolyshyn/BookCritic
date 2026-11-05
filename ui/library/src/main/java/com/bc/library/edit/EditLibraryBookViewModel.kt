package com.bc.library.edit

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.INavigation
import com.bc.model.repository.BookData
import com.bc.model.repository.IBookRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = EditLibraryBookViewModel.Factory::class)
class EditLibraryBookViewModel @AssistedInject constructor(
    @Assisted val bookId: String,
    private val repository: IBookRepository,
    private val navigation: INavigation,) : ViewModel() {
    private val _uiState = MutableStateFlow(EditLibraryBookUIState())
    val uiState: StateFlow<EditLibraryBookUIState> = _uiState.asStateFlow()

    private var originalBook: BookData? = null

    @AssistedFactory
    interface Factory {
        fun create(bookId: String): EditLibraryBookViewModel
    }

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            try {
                val book = repository.getBookById(bookId)
                originalBook = book

                _uiState.update { currentState ->
                    if (book == null) {
                        currentState.copy(
                            isLoading = false,
                        )
                    } else {
                        currentState.copy(
                            bookItem = EditLibraryBookItem(
                                title = book.title,
                                author = book.author,
                                rating = book.rating ?: 0f,
                                startedDate = book.startedDate,
                                finishedDate = book.finishedDate,
                                summary = book.summary ?: "",
                                note = book.note ?: "",
                                bookCover = book.coverPath,
                                selectedCoverUri = null,
                                isFavorite = book.isFavorite,
                            ),
                            isLoading = false,
                        )
                    }
                }
            } catch (exception: Exception) {
                Log.d(TAG, "Load Data Error: ${exception.message}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun onAction(action: EditLibraryBookUIAction) {
        when (action) {
            EditLibraryBookUIAction.BackClicked -> {
                navigation.goBack()
            }

            is EditLibraryBookUIAction.OnEditClicked -> {
                _uiState.update {
                    it.copy(isEditing = true)
                }
            }

            is EditLibraryBookUIAction.OnCoverSelected -> {
                onCoverSelected(action.uri)
            }

            EditLibraryBookUIAction.OnSaveClicked -> {
                viewModelScope.launch {
                    saveBook(_uiState.value)
                }
            }

            is EditLibraryBookUIAction.OnTitleChanged -> updateBookItem {
                copy(title = action.value)
            }

            is EditLibraryBookUIAction.OnAuthorChanged -> updateBookItem {
                copy(author = action.value)
            }

            is EditLibraryBookUIAction.OnRatingChanged -> updateBookItem {
                copy(rating = action.value)
            }

            is EditLibraryBookUIAction.OnStartedDateChanged -> updateBookItem {
                copy(startedDate = action.value)
            }

            is EditLibraryBookUIAction.OnFinishedDateChanged -> updateBookItem {
                copy(finishedDate = action.value)
            }

            is EditLibraryBookUIAction.OnSummaryChanged -> updateBookItem {
                copy(summary = action.value)
            }

            is EditLibraryBookUIAction.OnNoteChanged -> updateBookItem {
                copy(note = action.value)
            }
        }
    }

    private fun onCoverSelected(uri: Uri) {
        updateBookItem {
            copy(selectedCoverUri = uri.toString())
        }
    }

    private suspend fun saveBook(editValue: EditLibraryBookUIState) {
        if (!hasChanges()) {
            navigation.goBack()
            return
        }

        if (_uiState.value.isLoading) return

        _uiState.update { it.copy(isLoading = true) }

        try {
            val item = editValue.bookItem

            val bookItem = BookData(
                id = bookId,
                title = item.title,
                author = item.author,
                rating = item.rating,
                startedDate = item.startedDate,
                finishedDate = item.finishedDate,
                summary = item.summary,
                note = item.note,
                coverPath = item.bookCover,
                bookCost = item.bookCost.trim().toLongOrNull(),
                isFavorite = item.isFavorite,
            )

            val selectedCoverUri = item.selectedCoverUri?.let(Uri::parse)

            repository.saveBook(
                book = bookItem,
                selectedCoverUri = selectedCoverUri,
            )

            navigation.goBack()

        } catch (e: Exception) {
            Log.e(TAG, "Failed to save book", e)
        } finally {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun hasChanges(): Boolean {
        val original = originalBook ?: return false
        val current = _uiState.value.bookItem

        return current.title != original.title ||
                current.author != original.author ||
                current.rating != (original.rating ?: 0f) ||
                current.startedDate != original.startedDate ||
                current.finishedDate != original.finishedDate ||
                current.summary != (original.summary ?: "") ||
                current.note != (original.note ?: "") ||
                current.bookCover != original.coverPath ||
                current.selectedCoverUri != null ||
                current.bookCost.trim().toLongOrNull() != original.bookCost
    }

    private fun updateBookItem(
        update: EditLibraryBookItem.() -> EditLibraryBookItem,
    ) {
        _uiState.update { state ->
            state.copy(
                bookItem = state.bookItem.update(),
            )
        }
    }
    companion object {
        private const val TAG = "EditLibraryBookViewModel"
    }
}