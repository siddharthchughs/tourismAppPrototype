package com.example.readersapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.readersapp.createuser.CreateUserScreen
import com.example.readersapp.home.ReaderHomeScreen
import com.example.readersapp.login.LoginScreen
import com.example.readersapp.profile.ProfileScreen
import com.example.readersapp.search.SearchScreen
import com.example.readersapp.splash.SplashScreen
import com.example.readersapp.update.UpdateUserScreen

@Composable
fun NavigationManager() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ReaderRoute.SplashScreen.name
    ) {
        composable(ReaderRoute.SplashScreen.name) {
            SplashScreen(
                navController = navController
            )
        }

        composable(ReaderRoute.LoginScreen.name) {
            LoginScreen(
                navController = navController
            )
        }

        composable(ReaderRoute.CreateAccountScreen.name) {
            CreateUserScreen(
                navController = navController,
                pressUp = {navController.navigateUp()}
            )
        }

        composable(ReaderRoute.ReaderHomeScreen.name) {
            ReaderHomeScreen(
                navController = navController
            )
        }

        composable(ReaderRoute.SearchScreen.name) {
            SearchScreen(
                navController = navController
            )
        }

        composable(ReaderRoute.UpdateScreen.name) {
            UpdateUserScreen(
                navController = navController
            )
        }

        composable(ReaderRoute.ReaderStatusScreen.name) {
            ProfileScreen(
                navController = navController
            )
        }
    }
}