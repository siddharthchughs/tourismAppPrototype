package com.example.tourismappmockup.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tourismappmockup.Route
import com.example.tourismappmockup.detail.DetailScreen
import com.example.tourismappmockup.favorite.FavoriteScreen
import com.example.tourismappmockup.home.HomeScreen
import com.example.tourismappmockup.myprofile.MyProfileScreen
import com.example.tourismappmockup.trip.TripScreen

@Composable
fun BottomNavGraph(navHostController: NavHostController){
    NavHost(
        navController = navHostController,
        startDestination = BottomBarScreen.HomeScreen.route
    ){
        composable(route = BottomBarScreen.HomeScreen.route) {
            HomeScreen(navHostController = navHostController)
        }

        composable(route = BottomBarScreen.TripScreen.route) {
            TripScreen()
        }

        composable(route = BottomBarScreen.FavoriteScreen.route) {
            FavoriteScreen()
         }

        composable(route = BottomBarScreen.MyProfileScreen.route) {
            MyProfileScreen()
         }

        composable(route = BottomBarScreen.DetailScreen.route) {
            DetailScreen()
         }
    }
}
