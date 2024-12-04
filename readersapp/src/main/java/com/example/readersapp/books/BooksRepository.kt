package com.example.readersapp.books

import com.example.readersapp.setting.ApplicationSetting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface BooksRepository {
    suspend fun getBooksById(id: String)
}

class BooksRepositoryImpl @Inject constructor(
    private val bookNetworkDataSource: BookNetworkDataSource,
    private val applicationSetting: ApplicationSetting,
    private val dispatcher: CoroutineDispatcher
) : BooksRepository {
    override suspend fun getBooksById(id: String) {
        withContext(dispatcher) {
            try {
                val response = bookNetworkDataSource.getBooks(
                    baseUrl = applicationSetting.getBaseUrl().first().toString(),
                    id = id
                )
                return@withContext when (response) {
                    is BooksNetworkResult.BooksAvailable -> response
                    is BooksNetworkResult.UnExpectedError -> response
                    is BooksNetworkResult.UnExpectedResponse -> response
                }
            } catch (e: Exception) {
                return@withContext BooksNetworkResult.UnExpectedResponse(
                    message = e.message.toString(),
                    detailMessaage = e.printStackTrace().toString()
                )
            }
        }
    }
}