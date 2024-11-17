package com.example.movieapp.detail

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
import retrofit2.http.Path
import retrofit2.http.Query
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

sealed interface MovieDetailNetworkResult {
    data class MovieDetailAvailable(
        val movieResponse: MovieDetailNetworkClient.MoviesDetailResponseModel
    ) : MovieDetailNetworkResult

    data class UnExpectedResponse(
        val message: String,
        val detail: String
    ) : MovieDetailNetworkResult

    data class UnExpectedError(
        val code: Int
    ) : MovieDetailNetworkResult
}

interface MovieDetailNetworkDataSource {
    suspend fun getMoviesDetailResponse(
        baseUrl: String,
        movieId: Int,
        api_key: String
    ): MovieDetailNetworkResult
}

interface MovieDetailNetworkClient {

    @Serializable
    data class MoviesDetailResponseModel(
        val movieDetail: MovieInfo
    )

    @Serializable
    data class MovieInfo(
        val backdrop_path: String?,
        val listOfGenre: List<Genre>,
        val originOfCountry: List<Country>,
        val overview: String?,
        val poster_path: String?,
        val runtime: Int,
        val tagline: String?,
        val vote_count: Int?,
    )

    @Serializable
    data class Genre(
        val name: String
    )

    @Serializable
    data class Country(
        val id: List<Int>
    )

    @GET("movie/")
    suspend fun getMovieDetails(
        @Header("baseurl") baseUrl: String,
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String
    ): Response<MoviesDetailResponseModel>
}

class MovieDetailNetworkDataSourceImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val clientFactory: MovieDetailNetworkClientFactory
) : MovieDetailNetworkDataSource {
    override suspend fun getMoviesDetailResponse(
        baseUrl: String,
        movieId: Int,
        api_key: String
    ): MovieDetailNetworkResult = withContext(dispatcher) {
        try {
            val clientFactory = clientFactory.createClientFactory(baseUrl = baseUrl)
            val response = clientFactory.getMovieDetails(
                baseUrl = baseUrl,
                movieId = movieId,
                api_key = api_key
            )
            when {
                response.isSuccessful -> {
                    val result = response.body()!!
                    Timber.i("Response body:: ${response.body()}")
                    MovieDetailNetworkResult.MovieDetailAvailable(result)
                }

                response.code() == 401 -> {
                    MovieDetailNetworkResult.UnExpectedError(code = response.code())
                }

                else -> {
                    MovieDetailNetworkResult.UnExpectedError(code = response.code())
                }
            }

        } catch (t: Throwable) {
            return@withContext MovieDetailNetworkResult.UnExpectedResponse(
                message = t.message ?: "Unknown", detail = t.printStackTrace().toString()
            )
        }
    }
}

class MovieDetailNetworkClientFactory @Inject constructor() {
    fun createClientFactory(baseUrl: String): MovieDetailNetworkClient {
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
        return retrofit.create(MovieDetailNetworkClient::class.java)
    }
}
