package com.bc.book.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bc.model.repository.BookData
import com.bc.model.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookListUiState())
    val uiState: StateFlow<BookListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    fun onAction(action: BookListAction) {
        when (action) {
            BookListAction.AddBookClicked -> Unit
            is BookListAction.BookClicked -> Unit
            BookListAction.RetryClicked -> Unit
        }
    }

    suspend fun loadData() {
        val books = bookRepository.getBook("1")
        _uiState.update {
            it.copy(
                books = books?.let { listOf(it).mapBooks() } ?: emptyList()
            )
        }
    }

    private fun List<BookData>.mapBooks(): List<BookUiModel> =
        map { book ->
            BookUiModel(
                id = book.id,
                title = book.title,
            )
        }
}