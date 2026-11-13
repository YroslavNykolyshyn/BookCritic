package com.bc.home

import androidx.lifecycle.ViewModel
import com.bc.core.navigation.AddBookInProgress
import com.bc.core.navigation.AddLibraryBook
import com.bc.core.navigation.INavigation
import com.bc.model.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val navigation: INavigation,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    fun onAction(action: HomeUIAction) {
        when(action) {
            HomeUIAction.OnAddBookInProgress -> {
                navigation.goTo(AddBookInProgress)
            }

            is HomeUIAction.OnUpdateBookInProgress -> TODO()
        }
    }
}