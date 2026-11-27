package com.bc.home.book_in_progress.finish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.core.navigation.INavigation
import com.bc.model.repository.IBookRepository
import com.bc.model.repository.ReadingStatus
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = FinishBookViewModel.Factory::class)
class FinishBookViewModel @AssistedInject constructor(
    @Assisted val bookId: String,
    private val bookRepository: IBookRepository,
    private val navigation: INavigation,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(bookId: String): FinishBookViewModel
    }

    private val _uiState = MutableStateFlow(FinishBookUIState())
    val uiState = _uiState.asStateFlow()

    init {
        loadBook()
        observeGenres()
    }

    fun onAction(action: FinishBookUIAction) {
        when (action) {
            is FinishBookUIAction.OnRatingChanged ->
                update { copy(rating = action.rating) }

            is FinishBookUIAction.OnSummaryChanged ->
                update { copy(summary = action.summary) }

            is FinishBookUIAction.OnNoteChanged ->
                update { copy(note = action.note) }

            FinishBookUIAction.OnConfirm -> finishBook()
            FinishBookUIAction.OnBackPressed -> navigation.goBack()
            is FinishBookUIAction.OnCustomGenreAdded -> {
                addCustomGenre(action.name)
            }

            is FinishBookUIAction.OnGenreChanged -> {
                update {
                    copy(genre = action.genre)
                }
            }
        }
    }

    private fun update(
        transform: FinishBookUIState.() -> FinishBookUIState,
    ) {
        _uiState.update { it.transform() }
    }

    private fun loadBook() {
        viewModelScope.launch {
            runCatching {
                bookRepository.getBookById(bookId)
            }.onSuccess { book ->
                _uiState.update {
                    it.copy(
                        book = book,
                        genre = book?.genre,
                        rating = book?.rating ?: 0f,
                        summary = book?.summary.orEmpty(),
                        note = book?.note.orEmpty(),
                        isLoading = false,
                    )
                }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun finishBook() {
        val state = _uiState.value
        val book = state.book ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            runCatching {
                bookRepository.updateBook(
                    book.copy(
                        status = ReadingStatus.ALREADY_READ,
                        currentPage = book.totalPages ?: book.currentPage,
                        genre = state.genres.find { it.id == state.genre?.id },
                        rating = state.rating,
                        summary = state.summary.trim(),
                        note = state.note.trim(),
                        finishedDate = System.currentTimeMillis(),
                    )
                )
            }.onSuccess {
                navigation.goBack()
            }.onFailure {
                _uiState.update { state ->
                    state.copy(isSaving = false)
                }
            }
        }
    }

    private fun observeGenres() {
        viewModelScope.launch {
            bookRepository.observeGenres().collect { genres ->
                update {
                    copy(genres = genres)
                }
            }
        }
    }

    fun addCustomGenre(name: String) {
        viewModelScope.launch {
            val newGenre = bookRepository.addCustomGenre(name)
            update {
                copy(genre = newGenre)
            }
        }
    }
}