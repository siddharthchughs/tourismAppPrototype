package com.example.movieapp.movie

import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import com.example.movieapp.R
import com.example.movieapp.component.imageLoad
import com.example.movieapp.settings.ApplicationSetting
import com.example.movieapp.settings.IMAGE_NOT_AVAILABLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

sealed interface MovieHomeUIState {
    data object Loading : MovieHomeUIState

    data class Loaded(
        val id: Int,
        val page: Int,
        val movies: List<MovieUIItem>
    ) : MovieHomeUIState

    data class TerminalError(
        val error: String
    ) : MovieHomeUIState

    data class MovieUIItem(
        val description: String?,
        val favorite_count: Int,
        val id: Int,
        val item_count: Int,
        val list_type: String,
        val name: String,
        val poster_path: String?
    )
}

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    val applicationSetting: ApplicationSetting
) : ViewModel() {

    val movieUIState: Flow<MovieHomeUIState> = combine(
        applicationSetting.getBaseUrl(),
        applicationSetting.getImageBaseUrl(),
        applicationSetting.apiKey(),
        transform = ::transform
    )

    private suspend fun transform(
        baseUrl: String,
        imageBaseUrl: String,
        apiKey: String
    ): MovieHomeUIState {
        val response = movieRepository.getMovieList(
            baseUrl = baseUrl,
            page = 20,
            api_key = apiKey
        )

        return when (response) {
            is MovieNetworkResult.AvailableMovies -> {
                response.let {
                    val id = it.movieResponse.id
                    val page = it.movieResponse.page
                    val movieList = it.movieResponse.results.map { movieInfo ->
                        MovieHomeUIState.MovieUIItem(
                            description = movieInfo.description,
                            favorite_count = movieInfo.favorite_count,
                            id = movieInfo.id,
                            item_count = movieInfo.item_count,
                            list_type = movieInfo.list_type,
                            name = movieInfo.name,
                            poster_path = movieInfo.poster_path ?:IMAGE_NOT_AVAILABLE
                       )
                    }
                    MovieHomeUIState.Loaded(
                        id = id,
                        page = page,
                        movies = movieList
                    )
                }
            }

            is MovieNetworkResult.UnExpectedError -> {
                MovieHomeUIState.TerminalError(response.code.toString())
            }

            is MovieNetworkResult.UnExpectedResponse -> {
                MovieHomeUIState.TerminalError(error = response.message)
            }
        }
    }
}
