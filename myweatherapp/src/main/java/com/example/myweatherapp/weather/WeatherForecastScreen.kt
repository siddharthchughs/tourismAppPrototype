package com.example.myweatherapp.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.myweatherapp.R
import com.example.myweatherapp.component.ForecastSingleItem
import com.example.myweatherapp.component.WeatherTypeRowItem

@Composable
fun ForecastScreen(
    navigateToDetail:NavHostController,
    navigateToSettings: () -> Unit,
    navigateToCategorySelected: NavHostController
) {
    val weatherForecastViewModel: WeatherForecastViewModel = hiltViewModel()
    val weatherForecastUIState =
        weatherForecastViewModel.weatherUIState.collectAsStateWithLifecycle(initialValue = ForecastUIState.Loading)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ForecastToolbar(navigateToSettings = navigateToSettings)
        ForecastStructure(
            weatherUI = weatherForecastUIState.value,
            cityUIState = weatherForecastViewModel.cityForecastUIState.value,
            forecastListState = weatherForecastViewModel.forecastList,
            navigateToCategorySelected = navigateToCategorySelected,
            navigateToDetail = navigateToDetail
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastToolbar(
    navigateToSettings: () -> Unit
) {
    var isDisplayed by remember { mutableStateOf(false) }
    TopAppBar(
        modifier = Modifier
            .background(Color.Red),
        title = {
            Text(text = stringResource(R.string.title_daily_forecast))
        },
        navigationIcon = {

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
                        navigateToSettings()
                    }
                )
            }
        }
    )
}

@Composable
fun ForecastStructure(
    weatherUI: ForecastUIState,
    cityUIState : CityUI,
    forecastListState : List<WeatherForecastUI>,
    navigateToDetail: NavHostController,
    navigateToCategorySelected: NavHostController,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        when (weatherUI) {

            is ForecastUIState.Loading -> {
                ForecastProgressbar()
            }

            is ForecastUIState.Loaded -> {
                DayForecasts(
                    cityUIState = cityUIState,
                    weatherForecastList = forecastListState,
                    navigateToCategorySelected = navigateToCategorySelected,
                    navigateToDetail = navigateToDetail
                )
            }

            is ForecastUIState.TerminalError -> {
                ForecastErrorMessage(weatherUI.errorMessage)
            }

        }
    }
}


@Composable
fun DayForecasts(
    modifier: Modifier = Modifier,
    cityUIState: CityUI,
    weatherForecastList: List<WeatherForecastUI>,
    navigateToCategorySelected: NavHostController,
    navigateToDetail:NavHostController
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly) {

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = cityUIState.name,
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
        )
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 12.dp)
        ) {
            items(items = weatherForecastList,
                key = {
                    it.dt
                }
            ) {
                ForecastSingleItem(
                    id = it.dt,
                    label_SeeAll = stringResource(R.string.label_see_all),
                    navigateToCategorySelected = navigateToCategorySelected

                )
                WeatherTypeRow(
                    weatherTypeList = it.list,
                    navigateToDetail = navigateToDetail
                )
                Spacer(
                    modifier = Modifier
                        .heightIn(min = 12.dp)
                )
            }
        }
    }
}

@Composable
fun WeatherTypeRow(
    weatherTypeList: List<WeatherUI>,
    navigateToDetail: NavHostController
) {
    LazyRow {
        items(
            items = weatherTypeList,
            key = {
                it.id
            }
        ) {
            WeatherTypeRowItem(
                main = it.main,
                description = it.description,
                navigateToDetail = navigateToDetail

            )
        }
    }
}


@Composable
fun ForecastErrorMessage(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = errorMessage,
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = MaterialTheme.typography.displayMedium.fontSize
            )
        )
    }
}

@Composable
fun ForecastProgressbar(modifier: Modifier = Modifier) {
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