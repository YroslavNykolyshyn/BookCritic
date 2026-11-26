package com.bc.home.book_in_progress

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.INavigation
import com.bc.home.book_in_progress.add.BookInProgressItem
import com.bc.home.book_in_progress.add.BookInProgressState
import com.bc.model.repository.BookData
import com.bc.model.repository.BookGenre
import com.bc.model.repository.BookGenres
import com.bc.model.repository.IBookRepository
import com.bc.model.repository.ReadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    val genres: StateFlow<List<BookGenre>> = bookRepository.observeGenres()
        .map { list ->
            val (predefined, custom) = list.partition { it.id in BookGenres.ordered }
            predefined.sortedBy { BookGenres.ordered.indexOf(it.id) } +
                    custom.sortedBy { it.name.lowercase() }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch {
            genres.collect { genres ->
                _uiState.update { it.copy(genres = genres) }
            }
        }
    }

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

            is BookInProgressUIAction.OnAddCustomGenre -> {
                addCustomGenre(action.value)
            }
            is BookInProgressUIAction.OnGenreSelected -> {
               updateBookItem {
                   copy(genre = action.value)
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
                    genre = item.genre,
                ),
                selectedCoverUri = item.selectedCoverUri?.let(Uri::parse),
            )
            navigation.goBack()
        }
    }

    fun addCustomGenre(name: String) {
        viewModelScope.launch {
            val newGenre = bookRepository.addCustomGenre(name)
            updateBookItem {
                copy(genre = newGenre)
            }
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