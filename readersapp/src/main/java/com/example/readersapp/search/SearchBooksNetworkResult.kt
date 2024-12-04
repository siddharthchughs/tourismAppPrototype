package com.example.readersapp.search

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

sealed interface SearchBooksNetworkResult {
    data class BooksAvailable(
        val books: SearchBookNetworkClient.SearchBookResponse
    ) : SearchBooksNetworkResult

    data class UnExpectedError(
        val code: Int
    ) : SearchBooksNetworkResult

    data class UnExpectedResponse(
        val message: String,
        val detailMessaage: String
    ) : SearchBooksNetworkResult
}

interface SearchBooksNetworkDataSource {
    suspend fun getBooks(
        baseUrl: String,
        query: String
    ): SearchBooksNetworkResult
}

interface SearchBookNetworkClient {

    @Serializable
    data class SearchBookResponse(
        val totalItems: Int,
        val items: List<Books>
    )

    @Serializable
    data class Books(
        val id :String,
        val volumeInfo: VolumeInfo,
        val searchInfo: SearchInfo ?=  null
    )

    @Serializable
    data class VolumeInfo(
        val title: String,
        val authors: List<String>,
        val publisher: String,
        val publishedDate: String?,
        val description: String?,
        val categories: List<String>,
        val pageCount: Int,
        val averageRating: Double?,
        val imageLinks: ImageLinks,
        val language: String
    )

    @Serializable
    data class ImageLinks(
        val smallThumbnail: String,
        val thumbnail: String
    )

    @Serializable
    data class SearchInfo(
        val textSnippet: String?
    )

    @GET("v1/volumes")
    suspend fun showBooks(
        @Header("baseurl") baseUrl: String,
        @Query("q") query: String,
    ): Response<SearchBookResponse>

    @GET("books/v1/volumes/{id}")
    suspend fun getBooksByID(
        @Header("baseurl") baseUrl: String,
        @Path("id") id: String,
    ): Response<SearchBookResponse>

}

class SearchBookNetworkDataSourceImpl @Inject constructor(
    val dispatcher: CoroutineDispatcher,
    val clientFactory: SearchBookNetworkClientFactory
) : SearchBooksNetworkDataSource {
    override suspend fun getBooks(
        baseUrl: String,
        query: String
    ): SearchBooksNetworkResult = withContext(dispatcher) {
        try {
            val factoryClient = clientFactory.createClientFactory(baseUrl = baseUrl)
            val response = factoryClient.showBooks(
                baseUrl = baseUrl,
                query = query
            )
            when {
                response.isSuccessful -> {
                    val result = response.body()!!
               Timber.i("Response : ${result.items}")
                    SearchBooksNetworkResult.BooksAvailable(
                        books = result
                    )
                }

                else -> {
                    SearchBooksNetworkResult.UnExpectedError(code = response.code())
                }
            }
        } catch (t: Throwable) {
            Timber.i("Response : ${t.message} \n ${t.printStackTrace()}")
            return@withContext SearchBooksNetworkResult.UnExpectedResponse(
                message = t.message ?: "Unknown", detailMessaage = t.printStackTrace().toString()
            )
        }
    }
}

class SearchBookNetworkClientFactory @Inject constructor() {
    fun createClientFactory(baseUrl: String): SearchBookNetworkClient {
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
        return retrofit.create(SearchBookNetworkClient::class.java)
    }
}
