package com.example.myweatherapp.categoryselect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.myweatherapp.R

@Composable
fun CategorySelectScreen(
    id: String?,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CategorySelectToolbar(
            id = id,
            navHostController = navHostController
        )
        CategorySelectStructure()
    }
}

@Composable
fun CategorySelectStructure() {
    Column {
        Text(
            text = "Hello I am Detail page"
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectToolbar(
    id: String?,
    navHostController: NavHostController,
) {
    var isDisplayed by remember { mutableStateOf(false) }
    TopAppBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary),
        title = {
            Text(text = id.toString())
        },
        navigationIcon = {
            IconButton(onClick = { navHostController.navigateUp() }) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                isDisplayed = !isDisplayed
            }
            ) {
                Image(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.menu_more_vert),
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.primary,
                        blendMode = BlendMode.DstOut
                    )
                )
            }
            DropdownMenu(
                expanded = isDisplayed,
                onDismissRequest = {
                    isDisplayed = !isDisplayed
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.settings)
                        )
                    },
                    onClick = {

                    }
                )
            }
        }
    )
}


@Composable
fun WeatherOverview() {
    Column {

    }
}

