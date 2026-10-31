package com.bc.book.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.AddLibraryBook
import com.bc.core.navigation.BookDetails
import com.bc.core.navigation.INavigation
import com.bc.model.repository.IBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val navigation: INavigation,
    private val bookRepository: IBookRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    init {
        observeBooks()
    }

    fun onAction(action: LibraryUIAction) {
        when(action) {
            LibraryUIAction.AddBookClicked -> {
                navigation.goTo(AddLibraryBook)
            }

            LibraryUIAction.FilterClicked -> {
                // Open your filter UI.
            }

            is LibraryUIAction.SearchChanged -> {
                // Update searchQuery in the ViewModel.
            }
        }
    }

    private fun observeBooks() {
        viewModelScope.launch {
            bookRepository.observeBooks()
                .collect { books ->
                _uiState.update { state ->
                    state.copy(
                        books = books.map { book ->
                            LibraryBookUiModel(
                                id = book.id,
                                title = book.title,
                                author = book.author,
                                rating = book.rating,
                                coverPath = book.coverPath,
                                bookSummary = book.summary ?: "",
                                bookOptionalSummary = book.note ?: "",
                            )
                        },
                        isLoading = false
                    )
                }
            }
        }
    }
}