package com.example.movieapp.currentmovies

import coil.network.HttpException
import com.example.movieapp.settings.ApplicationSetting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Exception

interface CurrentMovieRepository {
    suspend fun getCurrentMovieList(
        baseUrl: String,
        api_key: String
    ): CurrentMovieNetworkResult
}

class CurrentMovieRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val applicationSetting: ApplicationSetting,
    private val movieNetworkDataSource: CurrentMovieNetworkDataSource
) : CurrentMovieRepository {
    override suspend fun getCurrentMovieList(
        baseUrl: String,
        api_key: String
    ): CurrentMovieNetworkResult = withContext(dispatcher) {
        try {
            val response = movieNetworkDataSource.getCurrentMoviesListResponse(
                baseUrl = baseUrl,
                api_key = api_key
            )
            when (response) {
                is CurrentMovieNetworkResult.AvailableMovies -> response
                is CurrentMovieNetworkResult.UnExpectedError -> response
                is CurrentMovieNetworkResult.UnExpectedResponse -> response
            }
        } catch (e: Exception) {
            return@withContext when (e) {
                is HttpException -> CurrentMovieNetworkResult.UnExpectedError(code = e.response.code)
                else -> CurrentMovieNetworkResult.UnExpectedResponse(
                    message = "Unknown error : ${e.message}",
                    detail = e.stackTraceToString()
                )
            }
        }
    }

}