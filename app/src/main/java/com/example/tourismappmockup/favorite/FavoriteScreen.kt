package com.example.tourismappmockup.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tourismappmockup.R

@Composable
fun FavoriteScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        FavoriteScreenToolbar()
        FavoriteScreenStructure()
    }
}

@Composable
fun FavoriteScreenStructure() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = stringResource(R.string.title_favorite))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreenToolbar(){
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        title = {
            Text(
                text = stringResource(R.string.title_favorite)
            )
        },
        navigationIcon = {

        },
        actions = {

        }
    )
}
