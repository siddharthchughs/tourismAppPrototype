package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.movieapp.component.rememberMovieNavController
import com.example.movieapp.ui.theme.TourismAppPrototypeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                MovieContent()
            }
        }
    }
}

@Composable
fun MovieAppTheme(content: @Composable () -> Unit) {
    TourismAppPrototypeTheme {
        content()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieContent() {
    val movieNavController = rememberMovieNavController()
    NavHost(
        navController = movieNavController.navHostController,
        startDestination = Route.Home.route
    ) {
        movieNavGraph(
            onMovieSelect = movieNavController,
            pressUp = movieNavController::pressUp,
            onCurrentMovies = movieNavController::navigateToCurrentMoviesList
            //              onMovieSelect = movieNavController::navigateToDetail
        )
    }
}

//enum class Route(
//    @StringRes
//    val title: Int,
//    val route: String
//) {
//    Home(title = R.string.title_home, route = "home"),
//    Detail(title = R.string.title_detail, route = "detail")
//}
sealed class Route(val route: String) {
    data object Home : Route("home")
    data object Setting : Route("Settings")
    data object CurrentMovies : Route("Now Playing")
    data object CurrentMovieDetail : Route("Movie Detail")
    data object Detail : Route("Detail")
}
