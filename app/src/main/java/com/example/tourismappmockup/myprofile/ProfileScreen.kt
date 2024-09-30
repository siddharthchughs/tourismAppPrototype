package com.example.tourismappmockup.myprofile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tourismappmockup.R

@Composable
fun MyProfileScreen(navHostController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyProfileScreenToolbar(
            navHostController = navHostController
        )
        MyProfileScreenStructure()
    }
}

@Composable
fun MyProfileScreenStructure() {

    var isVisible by  remember { mutableStateOf(false) }
    val minimumLines by remember {
        mutableStateOf(3)
    }
    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            text = stringResource(R.string.detail_description),
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            maxLines = if(isVisible) Int.MAX_VALUE else minimumLines
        )

        Text(
            text = if (isVisible) "Read less" else "Read more",
            modifier = Modifier
                .clickable(onClick = {
                  isVisible= !isVisible
                }
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreenToolbar(navHostController: NavHostController) {
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
            IconButton(onClick = { navHostController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                )
            }
        },
        actions = {

        }
    )
}

@Composable
fun MyProfile() {


}


@Composable
fun MyProfileCollapsableElement() {


}

