package com.example.tourismappmockup.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.tourismappmockup.R

sealed class BottomBarScreen(
    val route:String,
    val title:String,
    val icon:ImageVector) {

    object HomeScreen: BottomBarScreen(
        route = "Home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object TripScreen: BottomBarScreen(
        route = "Trips",
        title = "Trip",
        icon = Icons.Default.Home
    )
    object FavoriteScreen: BottomBarScreen(
        route = "Favorite",
        title = "Favorite",
        icon = Icons.Default.Home
    )

    object MyProfileScreen: BottomBarScreen(
        route = "My Profile",
        title = "My Profile",
        icon = Icons.Default.Home
    )
    object DetailScreen: BottomBarScreen(
        route = "Detail",
        title = "Detail",
        icon = Icons.Default.Info
    )

}