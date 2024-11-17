package com.example.bizcardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bizcardapp.myprofile.MyProfileScreen
import com.example.bizcardapp.myprofile.SelectionScreen
import com.example.bizcardapp.ui.theme.TourismAppPrototypeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TourismAppPrototypeTheme {
                CreateCard()
            }
        }
    }
}

@Composable
fun CreateCard() {
    val navController = rememberNavController()
    MainNavHostController(navHostController = navController)
}

@Composable
fun MainNavHostController(navHostController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = Route.IndexSelection.route,
    ) {
        composable(route = Route.IndexSelection.route) {
            SelectionScreen()
        }
    }
}


sealed class Route(val route: String) {
    object IndexSelection : Route("Selection")
    object MyProfile : Route("Profile")
    object Detail : Route("Detail")
}

@Preview(showBackground = true)
@Composable
fun DefaultGreetingPreview() {
    TourismAppPrototypeTheme {
    }
}