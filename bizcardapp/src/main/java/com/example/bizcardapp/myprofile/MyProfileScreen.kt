package com.example.bizcardapp.myprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bizcardapp.R
import com.example.bizcardapp.component.ProfileImage
import com.example.bizcardapp.component.ShowProjectButton
import com.example.bizcardapp.component.SingleHeaderText
import com.example.bizcardapp.component.SingleSubTitleText
import com.example.bizcardapp.viewmodel.MyProfileUIState
import com.example.bizcardapp.viewmodel.MyProfileViewModel

@Composable
fun MyProfileScreen(modifier: Modifier = Modifier) {
    val myProfileViewModel: MyProfileViewModel = hiltViewModel()
    val myProfileUIState =
        myProfileViewModel.myProfileUIState.collectAsStateWithLifecycle(initialValue = MyProfileUIState.Loading)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .safeContentPadding()
    ) {
        MyProfileTopAppBar()
        MyProfileScreenStructure(
            myProfileUIState = myProfileUIState.value,
             isClicked = myProfileViewModel.isbuttonClicked.value,
            eventClicked = myProfileViewModel::eventClicked,
        )
    }
}

@Composable
fun MyProfileScreenStructure(
    myProfileUIState: MyProfileUIState,
    isClicked: Boolean,
    eventClicked: (Boolean)->Unit,
) {
    when (myProfileUIState) {
        is MyProfileUIState.Loaded -> {
            Portfolio(
                projectUIState = myProfileUIState.projectsList,
                isClicked = isClicked,
                eventClicked = eventClicked,
            )
        }

        MyProfileUIState.Loading -> {
            ProgressBar()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileTopAppBar() {
    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(MaterialTheme.colorScheme.background),
        title = {
            Text(
                text = stringResource(R.string.title_app)
            )
        },
        navigationIcon = {},
        actions = {}
    )
}

@Composable
fun Portfolio(
    projectUIState: List<MyProfileUIState.ProjectUIState>,
    isClicked: Boolean,
    eventClicked: (Boolean) -> Unit,
    ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileCardContent()
        ShowProjectButton(
            isClicked = isClicked,
            eventClicked = eventClicked,
            profileUIState = projectUIState
        )
    }
}

@Composable
fun ProfileCardContent(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .width(350.dp)
            .height(300.dp)
            .padding(12.dp),
        shape = RoundedCornerShape(size = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
        ) {
            ProfileImage()
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Spacer(
                    modifier = modifier
                        .weight(0.1f)
                        .height(8.dp)
                )
                Divider(
                    modifier = modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(
                            MaterialTheme.colorScheme.primary
                        )
                )
                Spacer(
                    modifier = modifier
                        .weight(0.1f)
                        .height(8.dp)
                )
            }
            SingleHeaderText(modifier = modifier)
            SingleSubTitleText(modifier = modifier)
        }
    }
}

@Composable
fun ProgressBar(modifier: Modifier = Modifier) {
    Column {
        Spacer(
            modifier = modifier
                .weight(1f)
                .height(IntrinsicSize.Min)
        )

        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .progressSemantics()
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(
            modifier = modifier
                .weight(1f)
                .height(IntrinsicSize.Min)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyProfileScreen()
}
