package com.example.tourismappmockup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tourismappmockup.bottomnavigation.MainScreen
import com.example.tourismappmockup.detail.DetailScreen
import com.example.tourismappmockup.home.HomeScreen
import com.example.tourismappmockup.ui.theme.TourismAppMockUpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TourismAppMockUpTheme {
                MainScreen()
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
fun MainNavHostController(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Route.HomeScreen.route) {

        composable(route = Route.HomeScreen.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(route = Route.DetailScreen.route) {
            DetailScreen()
        }
    }
}

sealed class Route(val route: String) {
    object HomeScreen : Route("Home")
    object DetailScreen : Route("Detail")
}
