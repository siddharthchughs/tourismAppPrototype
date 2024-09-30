package com.example.bizcardapp.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bizcardapp.R
import com.example.bizcardapp.viewmodel.MyProfileUIState

@Composable
fun ShowProjectButton(
    isClicked: Boolean,
    eventClicked: (Boolean) -> Unit,
    profileUIState: List<MyProfileUIState.ProjectUIState>
) {
    val isToggled = remember { mutableStateOf(isClicked) }

    Button(
        onClick = {
            isToggled.value = !isClicked
            Log.i("Clicked","${isToggled.value}")
            eventClicked(isToggled.value)
        } ,
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.secondary.copy(0.2f)
        )
    ) {
        Text(
            text = stringResource(R.string.show_projects)
        )
    }
    if (isToggled.value)
        ProjectContent(profileUIState = profileUIState)
    else
        Box {}
}

@Composable
fun ProjectContent(profileUIState: List<MyProfileUIState.ProjectUIState>) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        PortfolioProjectList(profileUIState)
    }
}
