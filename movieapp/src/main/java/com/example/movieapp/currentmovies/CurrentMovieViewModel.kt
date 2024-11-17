package com.example.movieapp.currentmovies

import androidx.lifecycle.ViewModel
import com.example.movieapp.settings.ApplicationSetting
import com.example.movieapp.settings.IMAGE_NOT_AVAILABLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

sealed interface CurrentMovieHomeUIState {
    data object Loading : CurrentMovieHomeUIState

    data class Loaded(
        val page: Int,
        val currentMovies: List<CurrentMovieUIItem>
    ) : CurrentMovieHomeUIState

    data class TerminalError(
        val error: String
    ) : CurrentMovieHomeUIState

    data class CurrentMovieUIItem(
        val adult: String?,
        val backdrop_path: String?,
        val genre_ids: List<Int>,
        val id: Int,
        val item_count: Int,
        val title: String,
        val poster_path: String?,
        val overview: String,
    )

}

@HiltViewModel
class CurrentMovieViewModel @Inject constructor(
    private val movieRepository: CurrentMovieRepository,
    val applicationSetting: ApplicationSetting
) : ViewModel() {

    val movieUIState: Flow<CurrentMovieHomeUIState> = combine(
        applicationSetting.getBaseUrl(),
        applicationSetting.apiKey(),
        transform = ::transform
    )

    private suspend fun transform(
        baseUrl: String,
        apiKey: String
    ): CurrentMovieHomeUIState {
        val response = movieRepository.getCurrentMovieList(
            baseUrl = baseUrl,
            api_key = apiKey
        )

        return when (response) {
            is CurrentMovieNetworkResult.AvailableMovies -> {
                response.let {
                    val page = it.currentMovieResponse.page
                    val movieList = it.currentMovieResponse.results.map { movieInfo ->
                        CurrentMovieHomeUIState.CurrentMovieUIItem(
                            adult = movieInfo.adult,
                            overview = movieInfo.overview,
                            backdrop_path = movieInfo.backdrop_path,
                            id = movieInfo.id,
                            item_count = movieInfo.item_count,
                            title = movieInfo.title,
                            genre_ids = movieInfo.genre_ids.map {
                                it
                            },
                            poster_path = movieInfo.poster_path ?: IMAGE_NOT_AVAILABLE
                        )
                    }

                    CurrentMovieHomeUIState.Loaded(
                        page = page,
                        currentMovies = movieList
                    )
                }
            }

            is CurrentMovieNetworkResult.UnExpectedError -> {
                CurrentMovieHomeUIState.TerminalError(response.code.toString())
            }

            is CurrentMovieNetworkResult.UnExpectedResponse -> {
                CurrentMovieHomeUIState.TerminalError(error = response.message)
            }
        }
    }
}
