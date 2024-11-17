package com.example.movieapp.detail

import androidx.lifecycle.ViewModel
import com.example.movieapp.settings.ApplicationSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

sealed interface MovieDetailUIState {
    data object Loading : MovieDetailUIState
    data class Loaded(
        val movieDetailState: MovieInfoState
    ) : MovieDetailUIState

    data class TerminalError(
        val errorMessage: String
    ) : MovieDetailUIState

    data class MovieInfoState(
        val listOfGenre: List<GenreState>,
        val originOfCountry: List<CountryState>,
        //  val runtime: Int,
        //    val tagline: String?,
    )

    data class GenreState(
        val name: String
    )

    data class CountryState(
        val id: List<Int>
    )
}

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
    private val applicationSetting: ApplicationSetting
) : ViewModel() {

    val movieDetailUIState: Flow<MovieDetailUIState> = combine(
        applicationSetting.getBaseUrl(),
        applicationSetting.apiKey(),
        applicationSetting.getImageBaseUrl(),
        transform = ::transform
    )

    private suspend fun transform(
        baseUrl: String,
        apiKey: String,
        imageUrl: String
    ): MovieDetailUIState {


        val result = movieDetailRepository.getMovieDetailedInfo(
            baseUrl = baseUrl,
            movieId = 1184918,
            apiKey = apiKey
        )

        return when (result) {
            is MovieDetailNetworkResult.MovieDetailAvailable -> {
                val movieResponse = result.movieResponse.movieDetail
                val genre = movieResponse.listOfGenre.map {
                    MovieDetailUIState.GenreState(it.name)
                }
                val originCountry = movieResponse.originOfCountry.map {
                    MovieDetailUIState.CountryState(it.id)
                }

                MovieDetailUIState.Loaded(
                    MovieDetailUIState.MovieInfoState(
                        listOfGenre = genre,
                        originOfCountry = originCountry
                    )
                )
            }

            is MovieDetailNetworkResult.UnExpectedError -> {
                MovieDetailUIState.TerminalError(errorMessage = result.code.toString())
            }

            is MovieDetailNetworkResult.UnExpectedResponse -> {
                MovieDetailUIState.TerminalError(errorMessage = result.message)
            }
        }
    }
}
