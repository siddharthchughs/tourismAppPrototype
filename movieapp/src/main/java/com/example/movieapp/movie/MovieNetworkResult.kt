package com.example.movieapp.movie

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

sealed interface MovieNetworkResult {
    data class AvailableMovies(
        val movieResponse: MovieNetworkClient.MoviesResponseModel
    ) : MovieNetworkResult

    data class UnExpectedResponse(
        val message: String,
        val detail: String
    ) : MovieNetworkResult

    data class UnExpectedError(
        val code: Int
    ) : MovieNetworkResult
}

interface MovieNetworkDataSource {
    suspend fun getMoviesListResponse(
        baseUrl: String,
        page: Int,
        api_key: String
    ): MovieNetworkResult
}

interface MovieNetworkClient {

    @Serializable
    data class MoviesResponseModel(
        var id: Int,
        var page: Int,
        val results: List<Movies>
    )

    @Serializable
    data class Movies(
        val description: String?,
        val favorite_count: Int,
        val id: Int,
        val item_count: Int,
        val list_type: String,
        val name: String,
        val poster_path: String?
    )

    //    @GET("movie/550/lists?page=1&api_key=e620691df09a09e70058468621b6e1e6")
    @GET("movie/550/lists?")
    suspend fun getMovies(
        @Header("baseurl") baseUrl: String,
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): Response<MoviesResponseModel>
    //    @GET("movie/550/lists?page=1&api_key=e620691df09a09e70058468621b6e1e6")

//    @GET("movie/now_playing")
//    suspend fun getMoviesNowPlaying(
//        @Header("baseurl") baseUrl: String,
//        @Query("api_key") api_key: String
//    ): Response<MoviesResponseModel>
}


class MovieNetworkDataSourceImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    val clientFactory: MovieNetworkClientFactory,
    val applicationSetting: ApplicationSetting
) : MovieNetworkDataSource {
    override suspend fun getMoviesListResponse(
        baseUrl: String,
        page: Int,
        api_key: String
    ): MovieNetworkResult =
        withContext(dispatcher) {
            try {
                val clientFactory = clientFactory.createClientFactory(baseUrl = baseUrl)
                val response = clientFactory.getMovies(
                    baseUrl = baseUrl,
                    page = page,
                    api_key = api_key
                )
                when {
                    response.isSuccessful -> {
                        Timber.i("Response:: ${response.body()}")
                        val result = response.body()!!
                        Timber.i("Response body:: ${response.body()}")
                        MovieNetworkResult.AvailableMovies(movieResponse = result)
                    }

                    response.code() == 401 -> {
                        MovieNetworkResult.UnExpectedError(code = response.code())
                    }

                    else -> {
                        MovieNetworkResult.UnExpectedError(code = response.code())
                        //                        throw IllegalStateException("Not Found ${response.code()}")
                    }
                }
            } catch (t: Throwable) {
                return@withContext MovieNetworkResult.UnExpectedResponse(
                    message = t.message ?: "Unknown", detail = t.printStackTrace().toString()
                )
            }
        }
}

class MovieNetworkClientFactory @Inject constructor() {
    fun createClientFactory(baseUrl: String): MovieNetworkClient {
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
        return retrofit.create(MovieNetworkClient::class.java)
    }
}
