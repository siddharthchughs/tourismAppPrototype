package com.example.readersapp.navigation

enum class ReaderRoute {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    SearchScreen,
    UpdateScreen,
    ReaderStatusScreen;

    companion object {
        fun fromRoute(route: String?): ReaderRoute = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            SearchScreen.name -> SearchScreen
            UpdateScreen.name -> UpdateScreen
            ReaderStatusScreen.name -> ReaderStatusScreen
            null-> ReaderHomeScreen
            else->throw IllegalStateException("Unknown State")
        }
    }
}