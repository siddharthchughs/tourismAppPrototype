package com.example.movieapp.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.Route


@Composable
fun rememberMovieNavController(
    navHostController: NavHostController = rememberNavController()
): MovieNavController = remember(navHostController) {
    MovieNavController(navHostController)
}

@Stable
class MovieNavController(
    val navHostController: NavHostController
) {
    val currentRoute: String?
        get() = navHostController?.currentDestination?.route

    fun pressUp() {
        navHostController.navigateUp()
    }

    private val NavGraph.startDestination: NavDestination?
        get() = findNode(startDestination)

    fun navigateToCurrentMoviesList(){
        navHostController.navigate(Route.CurrentMovies.route)
    }

    fun navigateToCurrentMovieDetail(){
        navHostController.navigate(Route.Detail.route)
    }
    fun navigateToDetail(){
//        if(from.lifecycleResume()){
            navHostController.navigate(Route.Detail.route)
  //      }
    }

    fun NavBackStackEntry.lifecycleResume() =
        this.lifecycle.currentState == Lifecycle.State.RESUMED


}