package com.example.movieapp.movie

import com.example.movieapp.settings.ApplicationSetting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MoviesRepository {
    suspend fun getMovieList(
        baseUrl: String,
        page:Int,
        api_key:String
    ): MovieNetworkResult
}

class MoviesRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val applicationSetting: ApplicationSetting,
    private val movieNetworkDataSource: MovieNetworkDataSource
) : MoviesRepository {
    override suspend fun getMovieList(
        baseUrl: String,
        page: Int,
        api_key: String
    ): MovieNetworkResult = withContext(dispatcher) {
        val response = movieNetworkDataSource.getMoviesListResponse(
            baseUrl = baseUrl,
            page = page,
            api_key = api_key
        )
        when (response) {
            is MovieNetworkResult.AvailableMovies -> {
                response
            }

            is MovieNetworkResult.UnExpectedError -> {
                response
            }

            is MovieNetworkResult.UnExpectedResponse -> {
                response
            }
        }

    }
}