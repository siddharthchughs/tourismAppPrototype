package com.example.tourismappmockup.myprofile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.tourismappmockup.bottomnavigation.BottomBarScreen
import com.example.tourismappmockup.bottomnavigation.Route

fun NavGraphBuilder.profileNavGraph(navHostController: NavHostController) {
    navigation(
        route = Route.Profile.route,
        startDestination = ProfileScreen.ProfileInfo.route
    ) {
        composable(route = ProfileScreen.ProfileInfo.route) {
            MyProfileScreen(navHostController = navHostController)
        }
    }
}

sealed class ProfileScreen(val route: String) {
    object ProfileInfo : ProfileScreen("ProfileInfo")
}
