package com.example.myweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myweatherapp.categoryselect.CategorySelectScreen
import com.example.myweatherapp.detail.WeatherDetailScreen
import com.example.myweatherapp.settings.SettingScreen
import com.example.myweatherapp.ui.theme.TourismAppPrototypeTheme
import com.example.myweatherapp.weather.ForecastScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TourismAppPrototypeTheme {
                MainNavController()
            }
        }
    }
}

@Composable
fun MainNavController() {
    val navController = rememberNavController()
    MainNavHostController(navHostController = navController)
}

@Composable
fun MainNavHostController(navHostController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = Route.Forecast.route,
        modifier = modifier.padding(10.dp)
    ) {
        composable(route = Route.Forecast.route) {
            ForecastScreen(
                navigateToCategorySelected = navHostController,
                navigateToDetail = navHostController,
                navigateToSettings = {
                    navHostController.navigate(Route.Setting.route)
                }
            )
        }

        composable(
            route = Route.Detail.route+"/{main}"+"/{description}",
            arguments = listOf(
                navArgument("main") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType }
            )
        ) { backEntryType ->
            WeatherDetailScreen(
                main = backEntryType.arguments?.getString("main"),
                description = backEntryType.arguments?.getString("description"),
                navHostController = navHostController
            )
        }

        composable(
            route = Route.CategorySelect.route+"/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backEntryType ->
            CategorySelectScreen(
                id = backEntryType.arguments?.getString("id"),
                navHostController = navHostController
            )
        }

        composable(route = Route.Setting.route) {
            SettingScreen(navHostController = navHostController)
        }
    }
}


sealed class Route(val route: String) {
    object SplashScreen : Route("Splash")
    object Setting : Route("Settings")
    object Forecast : Route("Forecast")
    object CategorySelect : Route("Select")
    object Detail : Route("Detail")
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TourismAppPrototypeTheme {
        Greeting("Android")
    }
}