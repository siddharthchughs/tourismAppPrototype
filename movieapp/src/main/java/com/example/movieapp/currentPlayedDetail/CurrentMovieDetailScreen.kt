package com.example.movieapp.currentPlayedDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movieapp.R
import com.example.movieapp.component.CardBanner
import com.example.movieapp.component.CustomLabels
import com.example.movieapp.component.MovieTitleLabel
import timber.log.Timber

@Composable
fun CurrentMovieDetailScreen(
    pressUp: () -> Unit,
    backgroundPoster:String?,
    overview:String?,
    modifier: Modifier = Modifier,
    onCurrentDetailMovie :() -> Unit = {}
) {

    val currentMovieDetailViewModel: CurrentMovieDetailViewModel = hiltViewModel()
    val currentMovieDetailUIState= currentMovieDetailViewModel.currentMovieDetailUIState.collectAsStateWithLifecycle(CurrentMovieDetailUIState.Loading)


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        CurrentMovieDetailToolbar(
            pressUp = pressUp
        )
        CurrentMovieDetailStructure(
            backgroundPoster = backgroundPoster,
            overview = overview
        )
    }
}

@Composable
fun CurrentMovieDetailStructure(
    backgroundPoster:String?,
    overview:String?,
) {
    Spacer(
        modifier = Modifier
            .height(4.dp)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Timber.i("Poster ::$backgroundPoster")
        CardBanner(
            backgroundPoster = backgroundPoster
        )
        CurrentMovieDetailBottomInfo(
            overview = overview
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentMovieDetailToolbar(
    modifier: Modifier = Modifier,
    pressUp: () -> Unit
) {
    var isDisplayed by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .background(MaterialTheme.colorScheme.surface),
        title = {
            Text(
                modifier = modifier
                    .padding(start = 12.dp),
                text = stringResource(R.string.text_title_detail),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        },

        navigationIcon = {
            IconButton(
                onClick = {
                    pressUp()
                }, modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Gray)
            ) {
                Image(
                    modifier = modifier
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(16.dp),
                            clip = true

                        ),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.surface
                    ),

                    )
            }
        },

        actions = {
            IconButton(
                onClick = {
                    isDisplayed = !isDisplayed
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Gray)
            ) {
                Image(
                    modifier = Modifier
                        .padding(8.dp),
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = stringResource(R.string.menu_more_vert),
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.surface
                    ),
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
fun CurrentMovieDetailBottomInfo(
    overview:String?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieTitleLabel(
            movieTitle = overview
        )
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        CustomLabels(
            vote_count = "123",
            duration = "1hr 2min",
            releaseStatus = "released"
        )
    }
}

