package com.bc.library.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.INavigation
import com.bc.model.repository.IBookRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
                                selectedCoverUri = book.coverPath,
                            ),
                            isLoading = false,
                        )
                    }
                }
            } catch (exception: Exception) {
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

            is EditLibraryBookUIAction.OnCoverSelected -> {
                onCoverSelected(action.uri)
            }

            EditLibraryBookUIAction.OnSaveClicked -> {
                viewModelScope.launch(Dispatchers.Main.immediate) {
                    saveBook()
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
        //TODO()
    }

    private fun saveBook() {
        //TODO()
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
}