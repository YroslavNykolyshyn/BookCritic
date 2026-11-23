package com.bc.home.book_in_progress

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.INavigation
import com.bc.home.book_in_progress.add.BookInProgressItem
import com.bc.home.book_in_progress.add.BookInProgressState
import com.bc.model.repository.BookData
import com.bc.model.repository.IBookRepository
import com.bc.model.repository.ReadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BookInProgressViewModel @Inject constructor(
    private val navigation: INavigation,
    private val bookRepository: IBookRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(BookInProgressState())
    val uiState: StateFlow<BookInProgressState> = _uiState.asStateFlow()

    fun onAction(action: BookInProgressUIAction) {
        when(action) {
            BookInProgressUIAction.OnBackClicked -> {
                navigation.goBack()
            }
            is BookInProgressUIAction.OnCoverSelected -> {
                onCoverSelected(action.uri)
            }
            is BookInProgressUIAction.OnPageChanged -> {
                updateBookItem {
                    copy(totalPages = action.value)
                }
            }
            BookInProgressUIAction.OnSaveClicked -> {
                saveBookInProgress()
            }
            is BookInProgressUIAction.OnTitleChanged -> {
                updateBookItem {
                    copy(name = action.value)
                }
            }
            is BookInProgressUIAction.OnAuthorChanged -> {
                updateBookItem {
                    copy(author = action.value)
                }
            }
        }
    }

    private fun saveBookInProgress() {
        val item = _uiState.value.bookInProgressItem

        viewModelScope.launch {
            bookRepository.saveBook(
                book = BookData(
                    id = UUID.randomUUID().toString(),
                    title = item.name.trim(),
                    author = item.author.trim(),
                    status = ReadingStatus.READING,
                    totalPages = item.totalPages.toLong(),
                    currentPage = item.currentPage.toLong(),
                ),
                selectedCoverUri = item.selectedCoverUri?.let(Uri::parse),
            )
            navigation.goBack()
        }
    }

    private fun onCoverSelected(uri: Uri) {
        updateBookItem {
            copy(selectedCoverUri = uri.toString())
        }
    }

    private inline fun update(
        transform: BookInProgressState.() -> BookInProgressState,
    ) {
        _uiState.update { current ->
            current.transform()
        }
    }

    private inline fun updateBookItem(
        transform: BookInProgressItem.() -> BookInProgressItem,
    ) {
        update{
            copy(bookInProgressItem = bookInProgressItem.transform())
        }
    }
}