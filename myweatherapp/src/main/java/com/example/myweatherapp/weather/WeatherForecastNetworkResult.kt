package com.example.myweatherapp.weather

import com.example.myweatherapp.settings.ApplicationSetting
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


sealed interface WeatherForecastNetworkResult {
    data class AvailableWeatherList(
        val weatherForecastResponse: WeatherNetworkClient.ForecastResponseModel
    ) : WeatherForecastNetworkResult

    data class UnAuthorizedError(
        val code: Int
    ) : WeatherForecastNetworkResult

    data class UnAuthorizedResponse(
        val message: String,
        val detail: String
    ) : WeatherForecastNetworkResult
}

interface WeatherForecastNetworkDataSource {
    suspend fun getWeatherForecastList(
        baseUrl: String
    ): WeatherForecastNetworkResult
}

interface WeatherNetworkClient {

    @Serializable
    data class ForecastResponseModel(
        val city: CityModel,
        val cnt: Int,
        val cod: String,
        val list: List<Weather>,
        val message: Double
    )

    @Serializable
    data class Weather(
        val dt:String,
        val weather: List<WeatherModel>
    )

    @Serializable
    data class WeatherModel(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    )

    @Serializable
    data class CityModel(
        val coord: CoordModel,
        val country: String,
        val id: Int,
        val name: String,
        val population: Int,
        val timezone: Int
    )

    @Serializable
    data class CoordModel(
        val lat: Double,
        val lon: Double
    )

    @GET("daily?lat=44.34&lon=10.99&cnt=7&appid=9727c3b4a52d540720270ecbc267f4c4")
    suspend fun getWeatherForecastList(
        @Header("baseurl") baseUrl: String
    ): Response<ForecastResponseModel>
}

class WeatherForecastDataSourceImpl @Inject constructor(
    val clientNetworkFactory: WeatherForecastClientNetworkFactory,
    val applicationSetting: ApplicationSetting,
    val dispatcher: CoroutineDispatcher
) : WeatherForecastNetworkDataSource {
    override suspend fun getWeatherForecastList(
        baseUrl: String
    ): WeatherForecastNetworkResult = withContext(dispatcher) {
        try {
            val client =
                clientNetworkFactory.clientFactory(applicationSetting.getBaseUrl().first())
            val response = client.getWeatherForecastList(
                baseUrl = baseUrl
            )
            when {
                response.isSuccessful -> {
                    val weatherForecastResponse = response.body()!!
                    Timber.i("Response :: ${weatherForecastResponse}")
                    WeatherForecastNetworkResult.AvailableWeatherList(weatherForecastResponse = weatherForecastResponse)
                }

                response.code() == 401 -> {
                    WeatherForecastNetworkResult.UnAuthorizedError(response.code())
                }

                else -> throw IllegalStateException("Unhandled  response code :: ${response.code()}")
            }

        } catch (t: Throwable) {
            return@withContext WeatherForecastNetworkResult.UnAuthorizedResponse(
                message = t.message ?: "Unknown", detail = t.printStackTrace().toString()
            )
        }
    }
}

class WeatherForecastClientNetworkFactory @Inject constructor() {
    fun clientFactory(baseUrl: String): WeatherNetworkClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.SECONDS)
            .callTimeout(5, TimeUnit.SECONDS)
            .build()
        val gsonBuilder = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
        return retrofit.create(WeatherNetworkClient::class.java)
    }
}
