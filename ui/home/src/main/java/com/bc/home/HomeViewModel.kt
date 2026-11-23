package com.bc.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.AddBookInProgress
import com.bc.core.navigation.FinishBook
import com.bc.core.navigation.INavigation
import com.bc.model.repository.BookRepository
import com.bc.model.repository.ReadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val navigation: INavigation,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    init {
        observeBookInProgress()
    }

    fun onAction(action: HomeUIAction) {
        when(action) {
            HomeUIAction.OnAddBookInProgress -> {
                navigation.goTo(AddBookInProgress)
            }

            is HomeUIAction.OnCurrentPageChanged -> {
                updateReadingProgress(action.value)
            }
            is HomeUIAction.OnFinishBookInProgress -> {
                navigation.goTo(FinishBook(action.id))
            }
        }
    }

    private fun observeBookInProgress() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            bookRepository.observeBooks()
                .map { books ->
                    books.firstOrNull { it.status == ReadingStatus.READING }
                }
                .collect { book ->
                    _uiState.update {
                        it.copy(
                            bookInProgress = book,
                            isLoading = false,
                        )
                    }
                }
        }
    }

    private fun updateReadingProgress(currentPage: Long) {
        val book = _uiState.value.bookInProgress ?: return

        viewModelScope.launch {
            bookRepository.updateBook(
                book.copy(currentPage = currentPage)
            )
        }
    }
}