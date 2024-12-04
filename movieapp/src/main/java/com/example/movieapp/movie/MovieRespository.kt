package com.example.movieapp.movie

import coil.network.HttpException
import com.example.movieapp.settings.ApplicationSetting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import kotlin.Exception

interface MovieRepository {
    suspend fun getMovieList(
        page: Int,
        api_key: String
    ): MovieNetworkResult
}

class MovieRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    val applicationSetting: ApplicationSetting,
    private val movieNetworkDataSource: MovieNetworkDataSource
) : MovieRepository {
    override suspend fun getMovieList(
        page: Int,
        api_key: String
    ): MovieNetworkResult = withContext(dispatcher) {
        try {
            val response = movieNetworkDataSource.getMoviesListResponse(
                baseUrl = applicationSetting.getBaseUrl().first().toString(),
                page = page,
                api_key = api_key
            )
            when (response) {
                is MovieNetworkResult.AvailableMovies -> response
                is MovieNetworkResult.UnExpectedError -> response
                is MovieNetworkResult.UnExpectedResponse -> response
            }
        } catch (e: Exception) {
            return@withContext when (e) {
                is HttpException -> MovieNetworkResult.UnExpectedError(code = e.response.code)
                else -> MovieNetworkResult.UnExpectedResponse(
                    message = "Unknown error : ${e.message}",
                    detail = e.stackTraceToString()
                )
            }
        }
    }

}