package com.example.movieapp.currentPlayedDetail

import com.example.movieapp.settings.ApplicationSetting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CurrentMovieDetailRepository {
    suspend fun getMovieDetailedInfo(
        baseUrl: String, movieId: Int, apiKey: String
    ): CurrentMovieDetailNetworkResult
}

class CurrentMovieDetailRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    val currentMovieDetailNetworkDataSource: CurrentMovieDetailNetworkDataSource,
    val applicationSetting: ApplicationSetting
) : CurrentMovieDetailRepository {
    override suspend fun getMovieDetailedInfo(
        baseUrl: String, movieId: Int, apiKey: String
    ): CurrentMovieDetailNetworkResult {
        return withContext(dispatcher) {
            try {
                val result = currentMovieDetailNetworkDataSource.getMoviesDetailResponse(
                    baseUrl = baseUrl,
                    movieId = movieId,
                    api_key = apiKey
                )
                when (result) {
                    is CurrentMovieDetailNetworkResult.MovieDetailAvailable -> {
                        result
                    }

                    is CurrentMovieDetailNetworkResult.UnExpectedError -> {
                        result
                    }

                    is CurrentMovieDetailNetworkResult.UnExpectedResponse -> {
                        result
                    }
                }
            } catch (t: Throwable) {
                return@withContext CurrentMovieDetailNetworkResult.UnExpectedResponse(
                    message = t.message ?: "", detail = t.printStackTrace().toString()
                )
            }
        }
    }
}
