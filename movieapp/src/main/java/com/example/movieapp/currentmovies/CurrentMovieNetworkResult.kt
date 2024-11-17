package com.example.movieapp.currentmovies

import com.example.movieapp.settings.ApplicationSetting
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

sealed interface CurrentMovieNetworkResult {
    data class AvailableMovies(
        val currentMovieResponse: CurrentMovieNetworkClient.CurrentMoviesResponseModel
    ) : CurrentMovieNetworkResult

    data class UnExpectedResponse(
        val message: String,
        val detail: String
    ) : CurrentMovieNetworkResult

    data class UnExpectedError(
        val code: Int
    ) : CurrentMovieNetworkResult
}

interface CurrentMovieNetworkDataSource {
    suspend fun getCurrentMoviesListResponse(
        baseUrl: String,
        api_key: String
    ): CurrentMovieNetworkResult
}

interface CurrentMovieNetworkClient {

    @Serializable
    data class CurrentMoviesResponseModel(
        var page: Int,
        val results: List<CurrentMovies>
    )

    @Serializable
    data class CurrentMovies(
        val adult: String?,
        val backdrop_path: String?,
        val genre_ids: List<Int>,
        val id: Int,
        val item_count: Int,
        val title: String,
        val release_date: String?,
        val original_language: String?,
        val poster_path: String?,
        val overview: String,
    )

    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying(
        @Header("baseurl") baseUrl: String,
        @Query("api_key") api_key: String
    ): Response<CurrentMoviesResponseModel>
}


class CurrentMovieNetworkDataSourceImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    val clientFactory: CurrentMovieNetworkClientFactory,
    val applicationSetting: ApplicationSetting
) : CurrentMovieNetworkDataSource {
    override suspend fun getCurrentMoviesListResponse(
        baseUrl: String,
        api_key: String
    ): CurrentMovieNetworkResult =
        withContext(dispatcher) {
            try {
                val clientFactory = clientFactory.createClientFactory(baseUrl = baseUrl)
                val response = clientFactory.getMoviesNowPlaying(
                    baseUrl = baseUrl,
                    api_key = api_key
                )
                when {
                    response.isSuccessful -> {
                        Timber.i("Response:: ${response.body()}")
                        val result = response.body()!!
                        Timber.i("Response body:: ${response.body()}")
                        CurrentMovieNetworkResult.AvailableMovies(currentMovieResponse = result)
                    }

                    response.code() == 401 -> {
                        CurrentMovieNetworkResult.UnExpectedError(code = response.code())
                    }

                    else -> {
                        CurrentMovieNetworkResult.UnExpectedError(code = response.code())
                    }
                }
            } catch (t: Throwable) {
                return@withContext CurrentMovieNetworkResult.UnExpectedResponse(
                    message = t.message ?: "Unknown", detail = t.printStackTrace().toString()
                )
            }
        }
}

class CurrentMovieNetworkClientFactory @Inject constructor() {
    fun createClientFactory(baseUrl: String): CurrentMovieNetworkClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.SECONDS)
            .callTimeout(120, TimeUnit.SECONDS)
            .build()
        val gsonBuilder = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
        return retrofit.create(CurrentMovieNetworkClient::class.java)
    }
}
