package com.example.tourismappmockup.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tourismappmockup.detail.detailNavGraph
import com.example.tourismappmockup.favorite.FavoriteScreen
import com.example.tourismappmockup.home.HomeScreen
import com.example.tourismappmockup.myprofile.profileNavGraph
import com.example.tourismappmockup.trip.TripScreen

@Composable
fun BottomNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navHostController = navHostController)
        }

        composable(route = BottomBarScreen.Trip.route) {
            TripScreen()
        }

        composable(route = BottomBarScreen.Favorite.route) {
            FavoriteScreen()
        }

        profileNavGraph(navHostController = navHostController)
        detailNavGraph(navHostController = navHostController)
    }
}

sealed class Route(val route: String) {
    object Detail : Route("Detail")
    object Profile : Route("Profile")
}


