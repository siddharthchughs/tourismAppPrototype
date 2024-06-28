package com.example.tourismappmockup.detail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.tourismappmockup.bottomnavigation.Route

fun NavGraphBuilder.detailNavGraph(navHostController: NavHostController){
    navigation(
        route = Route.Detail.route,
        startDestination = DetailScreen.Information.route
    ){
        composable(route = DetailScreen.Information.route) {
            DetailScreen(navHostController = navHostController)
        }
    }
}

sealed class DetailScreen(val route: String) {
    object Information : DetailScreen("Info")
    object Location : DetailScreen("Location")
}
