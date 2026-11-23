package com.bc.library.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.AddLibraryBook
import com.bc.core.navigation.EditLibraryBook
import com.bc.core.navigation.INavigation
import com.bc.model.repository.IBookRepository
import com.bc.model.repository.ReadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.map

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

            LibraryUIAction.OnSearchStopped -> onSearchStopped()

            is LibraryUIAction.OnSearchChanged ->
                onSearchChanged(action.query)

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
            combine(
                bookRepository.observeBooks().map { books ->
                    books.filter { it.status == ReadingStatus.ALREADY_READ }
                },
                uiState.map { it.searchQuery }
                    .distinctUntilChanged(),
            ) { books, query ->
                books.filterBooks(query).map { it.toUiModel() }
            }.collect { books ->
                _uiState.update {
                    it.copy(
                        books = books,
                        isLoading = false,
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

    private fun onSearchStopped() {
        _uiState.update {
            it.copy(
                searchQuery = "",
            )
        }
    }

    private fun onSearchChanged(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
    }
}