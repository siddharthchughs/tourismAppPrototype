package com.example.movieapp

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movieapp.component.MovieNavController
import com.example.movieapp.currentPlayedDetail.CurrentMovieDetailScreen
import com.example.movieapp.currentmovies.CurrentMovieHomeScreen
import com.example.movieapp.detail.MovieDetailScreen
import com.example.movieapp.movie.MovieHomeScreen

fun NavGraphBuilder.movieNavGraph(
    pressUp: () -> Unit,
    onMovieSelect: MovieNavController,
    onCurrentMovies: () -> Unit
) {
    composable(route = Route.Home.route) {
        MovieHomeScreen(
            onMovieSelect = onMovieSelect,
            onCurrentMovies = onCurrentMovies
        )
    }
    composable(
        route = Route.Detail.route + "/{movieOverview}" + "/{backgroundPoster}",
        arguments = listOf(
            navArgument("backgroundPoster") { type = NavType.StringType },
            navArgument("movieOverview") { type = NavType.StringType },
        )
    ) { movieDetailarguments ->
        MovieDetailScreen(
            pressUp = pressUp,
            backgroundPoster = movieDetailarguments.arguments?.getString("backgroundPoster"),
            overview = movieDetailarguments.arguments?.getString("movieOverview")
        )
    }

    composable(route = Route.CurrentMovies.route) {
        CurrentMovieHomeScreen(
            pressUp = pressUp,
            onCurrentDetailMovie = onMovieSelect::navigateToCurrentMovieDetail
        )
    }

    composable(route = Route.CurrentMovieDetail.route) {
        CurrentMovieDetailScreen(
            pressUp = pressUp,
            backgroundPoster = "" ?: "",
            overview = "" ?: "",
            onCurrentDetailMovie = onMovieSelect::navigateToCurrentMovieDetail,
            )
    }


}