package com.example.myweatherapp.weather

import androidx.lifecycle.ViewModel
import com.example.myweatherapp.settings.ApplicationSetting
import com.example.myweatherapp.weather.WeatherNetworkClient.CityModel
import com.example.myweatherapp.weather.WeatherNetworkClient.Weather
import com.example.myweatherapp.weather.WeatherNetworkClient.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed interface ForecastUIState {
    data object Loading : ForecastUIState
    data class Loaded(val loadForecast: ForecastUI) : ForecastUIState
    data class TerminalError(
        val errorMessage: String
    ) : ForecastUIState
}

data class ForecastUI(
    val city: CityUI,
    val list: List<WeatherForecastUI>
)

data class WeatherForecastUI(
    val dt: String,
    val list: List<WeatherUI>
)

data class WeatherUI(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class CityUI(
    val country: String,
    val name: String,
    val id: Int
)


@HiltViewModel
class WeatherForecastViewModel @Inject constructor(
    private val weatherForecastNetworkDataSource: WeatherForecastNetworkDataSource,
    private val applicationSetting: ApplicationSetting
) : ViewModel() {

    val weatherUIState: Flow<ForecastUIState> = flow {
        emit(transform(applicationSetting.getBaseUrl().toString()))
    }

    private suspend fun transform(baseUrl: String): ForecastUIState {
        val response = weatherForecastNetworkDataSource.getWeatherForecastList(baseUrl)

        return when (response) {
            is WeatherForecastNetworkResult.AvailableWeatherList -> {
                val dayForecast = response.weatherForecastResponse.city.let(::transform)
                val forecast = response.weatherForecastResponse.list.let(::transformW)

                ForecastUIState.Loaded(
                    ForecastUI(
                        city = dayForecast,
                        list = forecast
                    )
                )
            }

            is WeatherForecastNetworkResult.UnAuthorizedError -> {
                ForecastUIState.TerminalError(response.code.toString())
            }

            is WeatherForecastNetworkResult.UnAuthorizedResponse -> {
                ForecastUIState.TerminalError(response.message)
            }
        }
    }

    private fun transform(cityModel: CityModel): CityUI {
        return CityUI(
            country = cityModel.country,
            name = cityModel.name,
            id = cityModel.id
        )
    }

    private fun transformW(weatherModel: List<Weather>): List<WeatherForecastUI> {
        return weatherModel.map {
            WeatherForecastUI(
                dt = it.dt,
                list = transformWM(it.weather)
            )
        }
    }

    private fun transformWM(weatherModel: List<WeatherModel>): List<WeatherUI> {
        return weatherModel.map {
            WeatherUI(
                description = it.description,
                main = it.main,
                id = it.id,
                icon = it.icon
            )
        }
    }
}