package com.example.readersapp.books

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface BooksUIState{
    data object Loading: BooksUIState
    data class BooksLoad(): BooksUIState
    data object Loading: BooksUIState


    data class BookState(

    )
}


@HiltViewModel
class BookViewModel @Inject constructor(
    private val booksRepository: BooksRepository
): ViewModel() {




}