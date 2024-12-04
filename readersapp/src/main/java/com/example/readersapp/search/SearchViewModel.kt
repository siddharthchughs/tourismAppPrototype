package com.example.readersapp.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.time.LocalDateTime
import javax.inject.Inject

sealed interface SearchBookUI {
    data object Idle : SearchBookUI
    data object Loading : SearchBookUI
    data class BooksLoaded(
        val booksInfo: SearchBooksState
    ) : SearchBookUI

    data class NotAvailable(
        val messsage: String
    ) : SearchBookUI
}

data class SearchBooksState(
    val totalItmes: Int,
    val items: List<BookState>
)

data class BookState(
    val id: String,
    val volumeInfo: VolumeInfoState
)

data class VolumeInfoState(
    val title: String,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val categories: List<String>?,
//    val pageCount: Int,
//    val averageRating: Double,
    val imageLinks: ImageLinkState?,
//    val language: String
)

data class ImageLinkState(
    val smallThumbnail: String,
)

data class SearchInfoState(
    val textSnippet: String?
)


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchBookRepository: SearchBookRepository
) : ViewModel() {

    val searchBookUIState = MutableStateFlow<SearchBookUI>(SearchBookUI.Idle)
    val searchBook = mutableStateOf("Unix")

    init {
        search()
    }

    fun onSearchUpdateChange(search: String) {
        searchBook.value = search
    }

    fun search() {
        viewModelScope.launch(Dispatchers.IO){
            val response = searchBookRepository.showBooks(query = searchBook.value)
            when (response) {
                is SearchBooksNetworkResult.BooksAvailable -> {
                    delay(1_000L)
                    val booksResponse = response.books
                    searchBookUIState.value = SearchBookUI.Loading
                    val books = booksResponse.let {
                        SearchBooksState(
                            totalItmes = it.totalItems,
                            items =
                            it.items.map {
                                transformToBookState(
                                    id = it.id,
                                    volumeInfo = it.volumeInfo
                                )
                            }
                        )
                    }
                    searchBookUIState.value = SearchBookUI.BooksLoaded(booksInfo = books)

                }

                is SearchBooksNetworkResult.UnExpectedError -> {
                    searchBookUIState.value =
                        SearchBookUI.NotAvailable(messsage = response.code.toString())
                }

                is SearchBooksNetworkResult.UnExpectedResponse -> {
                    searchBookUIState.value = SearchBookUI.NotAvailable(messsage = response.message)
                }
            }

        }
    }

    fun transformToBookState(
        id: String,
        volumeInfo: SearchBookNetworkClient.VolumeInfo,
    ): BookState {

        val volumeDetail = VolumeInfoState(
            title = volumeInfo.title,
            authors = volumeInfo.authors.map {
                it
            },
            publisher = volumeInfo.publisher ?: " ",
            publishedDate = volumeInfo.publishedDate ?: LocalDateTime.now().toString(),
             description = volumeInfo.description?: "NO Description",
            categories = volumeInfo.categories,
//  //          pageCount = volumeInfo.pageCount,
//            averageRating = volumeInfo.averageRating ?: 0.0,
//            language = volumeInfo.language,
            imageLinks = ImageLinkState(
                smallThumbnail = volumeInfo.imageLinks.smallThumbnail
            )
        )

        return BookState(
            id = id,
            volumeInfo = volumeDetail,
        )
    }
}