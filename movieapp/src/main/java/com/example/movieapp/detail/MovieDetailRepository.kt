package com.example.movieapp.detail

import com.example.movieapp.settings.ApplicationSetting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MovieDetailRepository {
    suspend fun getMovieDetailedInfo(
        baseUrl: String, movieId: Int, apiKey: String
    ): MovieDetailNetworkResult
}

class MovieDetailRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    val movieDetailNetworkDataSource: MovieDetailNetworkDataSource,
    val applicationSetting: ApplicationSetting
) : MovieDetailRepository {
    override suspend fun getMovieDetailedInfo(
        baseUrl: String, movieId: Int, apiKey: String
    ): MovieDetailNetworkResult {
        return withContext(dispatcher) {
            try {
                val result = movieDetailNetworkDataSource.getMoviesDetailResponse(
                    baseUrl = baseUrl,
                    movieId = movieId,
                    api_key = apiKey
                )
                when (result) {
                    is MovieDetailNetworkResult.MovieDetailAvailable -> {
                        result
                    }

                    is MovieDetailNetworkResult.UnExpectedError -> {
                        result
                    }

                    is MovieDetailNetworkResult.UnExpectedResponse -> {
                        result
                    }
                }
            } catch (t: Throwable) {
                return@withContext MovieDetailNetworkResult.UnExpectedResponse(
                    message = t.message ?: "", detail = t.printStackTrace().toString()
                )
            }
        }
    }
}
