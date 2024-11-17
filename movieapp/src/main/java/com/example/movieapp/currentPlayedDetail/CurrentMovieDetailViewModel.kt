package com.example.movieapp.currentPlayedDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movieapp.component.imageLoad
import com.example.movieapp.settings.ApplicationSetting
import com.example.movieapp.settings.BASE_IMAGE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

sealed interface CurrentMovieDetailUIState {
    data object Loading : CurrentMovieDetailUIState
    data class Loaded(
        val movieDetailState: MovieDetailInfoState
    ) : CurrentMovieDetailUIState

    data class TerminalError(
        val errorMessage: String
    ) : CurrentMovieDetailUIState

    data class MovieDetailInfoState(
        val backdrop_path: String?,
        val listOfGenre: List<GenreState>,
        val origin_title: String,
        val production_companies: List<ProductionCountriesState>,
        val overview: String?,
        val poster_path: String?,
        val runtime: Int,
        val tagline: String?,
        val vote_count: Int?,
    )

    data class GenreState(
        val id: Int,
        val name: String
    )

    data class ProductionCountriesState(
        val id: Int,
        val name: String
    )
}

@HiltViewModel
class CurrentMovieDetailViewModel @Inject constructor(
    private val movieDetailRepository: CurrentMovieDetailRepository,
    private val applicationSetting: ApplicationSetting
) : ViewModel() {


    val movieIdtext = mutableStateOf(0)

    val currentMovieDetailUIState: Flow<CurrentMovieDetailUIState> = combine(
        applicationSetting.getBaseUrl(),
        applicationSetting.apiKey(),
        applicationSetting.getImageBaseUrl(),
        transform = ::transform
    )

    private suspend fun transform(
        baseUrl: String,
        apiKey: String,
        imageBaseUrl:String
    ): CurrentMovieDetailUIState {

        val result = movieDetailRepository.getMovieDetailedInfo(
            baseUrl = baseUrl,
            movieId = movieIdtext.value,
            apiKey = apiKey
        )

        return when (result) {
            is CurrentMovieDetailNetworkResult.MovieDetailAvailable -> {
                val movieResponse = result.movieResponse.movieDetail
                val listOfGenre = movieResponse.listOfGenre.map {
                    CurrentMovieDetailUIState.GenreState(
                        id = it.id,
                        name = it.name
                    )
                }
                val productionCompanies = movieResponse.production_companies.map {
                    CurrentMovieDetailUIState.ProductionCountriesState(
                        id = it.id,
                        name = it.name
                    )
                }

                CurrentMovieDetailUIState.Loaded(
                    CurrentMovieDetailUIState.MovieDetailInfoState(
                        listOfGenre = listOfGenre,
                        production_companies = productionCompanies,
                        backdrop_path = BASE_IMAGE_URL.imageLoad(movieResponse.backdrop_path.toString()),
                        origin_title = movieResponse.origin_title,
                        overview = movieResponse.overview,
                        tagline = movieResponse.tagline,
                        poster_path = BASE_IMAGE_URL.imageLoad(movieResponse.backdrop_path.toString()),
                        runtime = movieResponse.runtime,
                        vote_count = movieResponse.vote_count
                    )
                )
            }

            is CurrentMovieDetailNetworkResult.UnExpectedError -> {
                CurrentMovieDetailUIState.TerminalError(errorMessage = result.code.toString())
            }

            is CurrentMovieDetailNetworkResult.UnExpectedResponse -> {
                CurrentMovieDetailUIState.TerminalError(errorMessage = result.message)
            }
        }
    }

}
