package com.example.tourismappmockup.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route:String,
    val title:String,
    val icon:ImageVector) {

    object Home: BottomBarScreen(
        route = "Home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Trip: BottomBarScreen(
        route = "Trips",
        title = "Trip",
        icon = Icons.Default.Home
    )

    object Favorite: BottomBarScreen(
        route = "Favorite",
        title = "Favorite",
        icon = Icons.Default.Home
    )

}