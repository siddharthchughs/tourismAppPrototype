package com.example.movieapp.movie

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.Route
import com.example.movieapp.component.MovieNavController
import com.example.movieapp.component.imageLoad
import com.example.movieapp.settings.BASE_IMAGE_URL
import com.example.movieapp.settings.IMAGE_NOT_AVAILABLE

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieHomeScreen(
    onMovieSelect: MovieNavController,
    onCurrentMovies: () -> Unit
) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    val movieHomeUIState =
        movieViewModel.movieUIState.collectAsStateWithLifecycle(initialValue = MovieHomeUIState.Loading)

    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .safeContentPadding()
        ) {
            MovieToolAppBar(
                onCurrentMovies = onCurrentMovies
            )
            MovieScreenStructure(
                homeUIState = movieHomeUIState.value,
                onMovieSelect = onMovieSelect
            )
        }
    }
}

@Composable
fun MovieScreenStructure(
    homeUIState: MovieHomeUIState,
    onMovieSelect: MovieNavController

) {
    when (homeUIState) {
        is MovieHomeUIState.Loaded -> {
            MovieList(
                list = homeUIState.movies,
                onMovieSelect = onMovieSelect
            )
        }

        MovieHomeUIState.Loading -> Progressbar()
        is MovieHomeUIState.TerminalError -> {
            TerminalError(
                error = homeUIState.error
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieToolAppBar(
    modifier: Modifier = Modifier,
    onCurrentMovies:()-> Unit
) {
    TopAppBar(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(MaterialTheme.colorScheme.primary),
        title = {
            stringResource(R.string.text_title_home)

        },
        navigationIcon = {

        },
        actions = {
            IconButton(onClick = {
                onCurrentMovies()
            }
            ) {

                Image(
                    painter = painterResource(R.drawable.current_list),
                    contentDescription = stringResource(R.string.menu_more_vert),
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
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
fun MovieList(
    list: List<MovieHomeUIState.MovieUIItem>,
    onMovieSelect: MovieNavController

) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = list, key = {
            it.id
        }) {
            MovieSingleItem(
                movieUIItem = it,
                onMovieSelect = onMovieSelect,
                movieOverview = it.name
            )
        }
    }
}

@Composable
fun MovieSingleItem(
    movieUIItem: MovieHomeUIState.MovieUIItem,
    onMovieSelect: MovieNavController,
    movieOverview: String?
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
                    if (movieUIItem.poster_path == IMAGE_NOT_AVAILABLE)
                        onMovieSelect.navHostController.navigate(Route.Detail.route + "/${movieOverview}" + "/${movieUIItem.poster_path}")
                    else
                        onMovieSelect.navHostController.navigate(Route.Detail.route + "/${movieOverview}" + "${movieUIItem.poster_path}")
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
            Text(
                text = movieUIItem.name,
                modifier = Modifier
                    .padding(8.dp, 10.dp)
            )
        }
    }
}

@Composable
fun Progressbar(modifier: Modifier = Modifier) {
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
fun TerminalError(error: String) {
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
