package com.example.tourismappmockup.myprofile

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
import androidx.navigation.NavHostController
import com.example.tourismappmockup.R
import com.example.tourismappmockup.home.HomeScreenStructure

@Composable
fun MyProfileScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyProfileScreenToolbar()
        MyProfileScreenStructure()
    }
}

@Composable
fun MyProfileScreenStructure() {
    Column {
        Text(text = stringResource(R.string.title_myprofile))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreenToolbar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        title = {
            Text(
                text = stringResource(R.string.title_myprofile)
            )
        },
        navigationIcon = {

        },
        actions = {

        }
    )
}

