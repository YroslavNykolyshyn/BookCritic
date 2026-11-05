package com.bc.library.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.AddLibraryBook
import com.bc.core.navigation.EditLibraryBook
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
            LibraryUIAction.OnAddBookClicked -> {
                navigation.goTo(AddLibraryBook)
            }

            LibraryUIAction.OnFilterClicked -> {
                // Open your filter UI.
            }

            is LibraryUIAction.OnSearchChanged -> {
                // Update searchQuery in the ViewModel.
            }

            is LibraryUIAction.OnViewBookClicked -> {
                navigation.goTo(EditLibraryBook(action.id))
            }

            is LibraryUIAction.OnFavoriteBookClicked -> {
               updateFavorite(action.id)
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
                                isFavorite = book.isFavorite,
                            )
                        },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun updateFavorite(bookId: String) {
        viewModelScope.launch {
            bookRepository.toggleFavorite(bookId)
        }
    }
}