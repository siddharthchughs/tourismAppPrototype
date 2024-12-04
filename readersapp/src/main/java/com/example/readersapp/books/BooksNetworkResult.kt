package com.example.readersapp.books

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
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

sealed interface BooksNetworkResult {
    data class BooksAvailable(
        val books: BookNetworkClient.BookResponseModel
    ) : BooksNetworkResult

    data class UnExpectedError(
        val code: Int
    ) : BooksNetworkResult

    data class UnExpectedResponse(
        val message: String,
        val detailMessaage: String
    ) : BooksNetworkResult
}

interface BookNetworkDataSource {
    suspend fun getBooks(
        baseUrl: String,
        id: String
    ): BooksNetworkResult
}

interface BookNetworkClient {

    @Serializable
    data class BookResponseModel(
        val totalItems: Int,
        val items: List<BookModel>
    )

    @Serializable
    data class BookModel(
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

   @GET("books/v1/volumes/{id}")
    suspend fun getBooksByID(
        @Header("baseurl") baseUrl: String,
        @Path("id") id: String,
    ): Response<BookResponseModel>

}

class BookNetworkDataSourceImpl @Inject constructor(
    val dispatcher: CoroutineDispatcher,
    val clientFactory: BookNetworkClientFactory
) : BookNetworkDataSource {
    override suspend fun getBooks(
        baseUrl: String,
        id: String
    ): BooksNetworkResult = withContext(dispatcher) {
        try {
            val factoryClient = clientFactory.createClientFactory(baseUrl = baseUrl)
            val response = factoryClient.getBooksByID(
                baseUrl = baseUrl,
                id = id
            )
            when {
                response.isSuccessful -> {
                    val result = response.body()!!
               Timber.i("Response : ${result.items}")
                    BooksNetworkResult.BooksAvailable(
                        books = result
                    )
                }

                else -> {
                    BooksNetworkResult.UnExpectedError(code = response.code())
                }
            }
        } catch (t: Throwable) {
            Timber.i("Response : ${t.message} \n ${t.printStackTrace()}")
            return@withContext BooksNetworkResult.UnExpectedResponse(
                message = t.message ?: "Unknown", detailMessaage = t.printStackTrace().toString()
            )
        }
    }
}

class BookNetworkClientFactory @Inject constructor() {
    fun createClientFactory(baseUrl: String): BookNetworkClient {
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
        return retrofit.create(BookNetworkClient::class.java)
    }
}
