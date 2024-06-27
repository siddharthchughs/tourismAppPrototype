package com.example.tourismappmockup.trip

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
fun TripScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        TripScreenToolbar()
        TripScreenStructure()
    }
}

@Composable
fun TripScreenStructure() {
    Column {
        Text(text = stringResource(R.string.title_trip))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripScreenToolbar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        title = {
            Text(
                text = stringResource(R.string.title_trip)
            )
        },
        navigationIcon = {

        },
        actions = {

        }
    )
}

