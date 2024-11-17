package com.example.movieapp.currentmovies

import android.annotation.SuppressLint
import android.widget.ImageButton
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.Route
import com.example.movieapp.component.MovieNavController
import com.example.movieapp.component.imageLoad
import com.example.movieapp.settings.BASE_IMAGE_URL
import com.example.movieapp.settings.IMAGE_NOT_AVAILABLE

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CurrentMovieHomeScreen(
    pressUp: () -> Unit,
    onCurrentDetailMovie: () -> Unit = {}
) {
    val movieViewModel: CurrentMovieViewModel = hiltViewModel()
    val movieHomeUIState =
        movieViewModel.movieUIState.collectAsStateWithLifecycle(initialValue = CurrentMovieHomeUIState.Loading)

    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .safeContentPadding()
        ) {
            CurrentMovieToolAppBar(
                pressUp = pressUp
            )
            CurrentMovieStructure(
                homeUIState = movieHomeUIState.value,
                onCurrentDetailMovie = onCurrentDetailMovie
            )
        }
    }
}

@Composable
fun CurrentMovieStructure(
    homeUIState: CurrentMovieHomeUIState,
    onCurrentDetailMovie: () -> Unit = {}
) {
    when (homeUIState) {
        is CurrentMovieHomeUIState.Loaded -> {
            CurrentMovieList(
                listCurrentMovies = homeUIState.currentMovies,
                        onCurrentDetailMovie = onCurrentDetailMovie
            )
        }

        CurrentMovieHomeUIState.Loading -> CurrentProgressbar()
        is CurrentMovieHomeUIState.TerminalError -> {
            CurrentTerminalError(
                error = homeUIState.error
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentMovieToolAppBar(
    modifier: Modifier = Modifier,
    pressUp: () -> Unit = {}
) {
    TopAppBar(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(MaterialTheme.colorScheme.primary),
        title = {
            stringResource(R.string.text_title_home)

        },
        navigationIcon = {
            IconButton(
                onClick = pressUp
            ) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Magenta)
                )

            }
        },
        actions = {

        }
    )
    Divider(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Magenta),
        thickness = 2.dp,
        color = Color.Red
    )
}

@Composable
fun CurrentMovieList(
    listCurrentMovies: List<CurrentMovieHomeUIState.CurrentMovieUIItem>,
    onCurrentDetailMovie: () -> Unit = {}
    ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = listCurrentMovies, key = {
            it.id
        }) {
            CurrentMovieSingleItem(
                movieUIItem = it,
                backgroundPoster = it.poster_path.toString(),
                movieOverview = it.title,
                onCurrentDetailMovie = onCurrentDetailMovie
            )
        }
    }
}

@Composable
fun CurrentMovieSingleItem(
    movieUIItem: CurrentMovieHomeUIState.CurrentMovieUIItem,
    backgroundPoster: String?,
    movieOverview: String?,
    onCurrentDetailMovie: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .padding(8.dp, 8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RectangleShape

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically)
                .clickable {
                    onCurrentDetailMovie()
//                    if (movieUIItem.poster_path == IMAGE_NOT_AVAILABLE)
//                        onMovieSelect.navHostController.navigate(Route.Detail.route + "/${movieOverview}" + "/${movieUIItem.poster_path}")
//                    else
//                        onMovieSelect.navHostController.navigate(Route.Detail.route + "/${movieOverview}" + "${movieUIItem.poster_path}")
                }
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(250.dp),
                alignment = Alignment.Center,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BASE_IMAGE_URL.imageLoad(movieUIItem.poster_path.toString()))
                    .placeholder(R.drawable.ic_launcher_background)
                    .crossfade(true)
                    .build(),
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = null
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    text = movieUIItem.title,
                    modifier = Modifier
                        .padding(8.dp, 10.dp)
                )

                LazyRow {
                    items(items = movieUIItem.genre_ids) {
                        Text(
                            text = it.toString(),
                            modifier = Modifier
                                .padding(8.dp, 10.dp)
                        )
                    }
                }

                Button(onClick = {

                }) {

                }
            }
        }
    }
}

@Composable
fun CurrentProgressbar(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(
            modifier = modifier
                .weight(1f)
        )
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 4.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(
            modifier = modifier
                .weight(1f)
        )

    }
}

@Composable
fun CurrentTerminalError(error: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            text = error,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Medium
            )
        )
    }
}
