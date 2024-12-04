package com.example.readersapp.search

import com.example.readersapp.setting.ApplicationSetting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SearchBookRepository {
    suspend fun showBooks(
        query: String
    ): SearchBooksNetworkResult
}

class earchBookRepositoryImpl @Inject constructor(
    val searchBooksNetworkDataSource: SearchBooksNetworkDataSource,
    val dispatcher: CoroutineDispatcher,
    private val applicationSetting: ApplicationSetting
) : SearchBookRepository {
    override suspend fun showBooks(
        query: String
    ): SearchBooksNetworkResult = withContext(dispatcher) {
        try {
            val result = searchBooksNetworkDataSource.getBooks(
                baseUrl = applicationSetting.getBaseUrl().first().toString(),
                query = query
            )
            when (result) {
                is SearchBooksNetworkResult.BooksAvailable -> result

                is SearchBooksNetworkResult.UnExpectedError -> result

                is SearchBooksNetworkResult.UnExpectedResponse -> result
            }
        } catch (e: Exception) {
            SearchBooksNetworkResult.UnExpectedResponse(
                message = e.message.toString(),
                detailMessaage = "UnKnown Error : ${e.printStackTrace()}"
            )
        }

    }
}