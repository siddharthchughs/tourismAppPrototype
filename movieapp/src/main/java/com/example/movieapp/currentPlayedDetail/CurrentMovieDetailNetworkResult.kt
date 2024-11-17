package com.example.movieapp.currentPlayedDetail

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

sealed interface CurrentMovieDetailNetworkResult {
    data class MovieDetailAvailable(
        val movieResponse: CurrentMovieDetailNetworkClient.MoviesDetailResponseModel
    ) : CurrentMovieDetailNetworkResult

    data class UnExpectedResponse(
        val message: String,
        val detail: String
    ) : CurrentMovieDetailNetworkResult

    data class UnExpectedError(
        val code: Int
    ) : CurrentMovieDetailNetworkResult
}

interface CurrentMovieDetailNetworkDataSource {
    suspend fun getMoviesDetailResponse(
        baseUrl: String,
        movieId: Int,
        api_key: String
    ): CurrentMovieDetailNetworkResult
}

interface CurrentMovieDetailNetworkClient {

    @Serializable
    data class MoviesDetailResponseModel(
        val movieDetail: MovieInfo
    )

    @Serializable
    data class MovieInfo(
        val backdrop_path: String?,
        val listOfGenre: List<Genre>,
        val origin_title:String,
        val production_companies: List<ProductionCountries>,
        val overview: String?,
        val poster_path: String?,
        val runtime: Int,
        val tagline: String?,
        val vote_count: Int?,
    )

    @Serializable
    data class Genre(
        val id:Int,
        val name: String
    )

    @Serializable
    data class ProductionCountries(
        val id:Int,
        val logo_path:String?,
        val name:String,
        val origin_country:String?
    )

    @GET("movie/")
    suspend fun getCurrentMovieDetails(
        @Header("baseurl") baseUrl: String,
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String
    ): Response<MoviesDetailResponseModel>

}

class CurrentMovieDetailNetworkDataSourceImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val clientFactory: MovieDetailNetworkClientFactory
) : CurrentMovieDetailNetworkDataSource {
    override suspend fun getMoviesDetailResponse(
        baseUrl: String,
        movieId: Int,
        api_key: String
    ): CurrentMovieDetailNetworkResult = withContext(dispatcher) {
        try {
            val clientFactory = clientFactory.createClientFactory(baseUrl = baseUrl)
            val response = clientFactory.getCurrentMovieDetails(
                baseUrl = baseUrl,
                movieId = movieId,
                api_key = api_key
            )
            when {
                response.isSuccessful -> {
                    val result = response.body()!!
                    Timber.i("Response body:: ${response.body()}")
                    CurrentMovieDetailNetworkResult.MovieDetailAvailable(result)
                }

                response.code() == 401 -> {
                    CurrentMovieDetailNetworkResult.UnExpectedError(code = response.code())
                }

                else -> {
                    CurrentMovieDetailNetworkResult.UnExpectedError(code = response.code())
                }
            }

        } catch (t: Throwable) {
            return@withContext CurrentMovieDetailNetworkResult.UnExpectedResponse(
                message = t.message ?: "Unknown", detail = t.printStackTrace().toString()
            )
        }
    }
}

class MovieDetailNetworkClientFactory @Inject constructor() {
    fun createClientFactory(baseUrl: String): CurrentMovieDetailNetworkClient {
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
        return retrofit.create(CurrentMovieDetailNetworkClient::class.java)
    }
}
