package com.example.tourismappmockup.bottomnavigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tourismappmockup.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navHostController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navHostController) }
    ) {
        BottomNavGraph(navHostController = navHostController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val bottomBarScreenList = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Trip,
        BottomBarScreen.Favorite
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = bottomBarScreenList.any {
        it.route == currentDestination?.route
    }
    if (bottomBarDestination) {
        NavigationBar(
            modifier = Modifier.background(MaterialTheme.colorScheme.onSurfaceVariant),
            tonalElevation = 4.dp
        ) {
            bottomBarScreenList.forEach { screens ->
                AddItem(
                    screens = screens,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screens: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavController
) {
    NavigationBarItem(
        label = {
            Text(text = screens.title)
        },

        icon = {
            Icon(
                imageVector = screens.icon,
                contentDescription = stringResource(R.string.label_navigation)
            )
        },

        selected = currentDestination?.hierarchy?.any {
            it.route == screens.route
        } == true,

        onClick = {
            navController.navigate(screens.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },

        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.secondary,
            unselectedTextColor = LocalContentColor.current.copy(alpha = 0.5f)
        )
    )
}




