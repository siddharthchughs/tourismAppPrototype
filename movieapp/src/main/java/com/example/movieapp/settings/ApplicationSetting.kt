package com.example.movieapp.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val BASE_URL_PREFERENCE = stringPreferencesKey("baseUrl")
private val USER_TOKEN_PREFERENCE = stringPreferencesKey("token")
private val USER_API_KEY_PREFERENCE = stringPreferencesKey("api_key")
private val USER_AUTH_PREFERENCE = stringPreferencesKey("auth")
const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original/"
const val IMAGE_NOT_AVAILABLE = "UNKNOWN"

interface ApplicationSetting {
    fun authorization(): Flow<String>
    suspend fun saveAuthorization(storeAuth: String)
    fun getBaseUrl(): Flow<String>
    suspend fun saveBaseUrl(baseUrl: String)
    fun getImageBaseUrl(): Flow<String>
    suspend fun saveImageBaseUrl(baseUrl: String)
    fun apiKey(): Flow<String>
    suspend fun saveApiKey(apiKey: String)

}

class ApplicationSettingImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ApplicationSetting {

    override fun authorization(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val authorization = preferences[USER_AUTH_PREFERENCE]
                    ?: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlNjIwNjkxZGYwOWEwOWU3MDA1ODQ2ODYyMWI2ZTFlNiIsIm5iZiI6MTcyNzg2MTE1My42NzUzNjQsInN1YiI6IjYyNDJiODRhZDcxZmI0MDA0N2Y4NDY3ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.qe36qeLMncvDfAkifxidnmmrJftzFlQpNuB6Od555tE"
                authorization
            }
    }

    override suspend fun saveAuthorization(storeAuth: String) {
        dataStore.edit { auth ->
            auth[USER_AUTH_PREFERENCE] = storeAuth
        }
    }

    override fun getBaseUrl(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val baseUrl = preferences[BASE_URL_PREFERENCE] ?: "https://api.themoviedb.org/3/"
                baseUrl
            }
    }

    override suspend fun saveBaseUrl(baseUrl: String) {
        dataStore.edit { storeBaseurl ->
            storeBaseurl[BASE_URL_PREFERENCE] = baseUrl
        }
    }

    override fun getImageBaseUrl(): Flow<String> {
     return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val baseUrl = preferences[BASE_URL_PREFERENCE] ?: BASE_IMAGE_URL
                baseUrl
            }
    }

    override suspend fun saveImageBaseUrl(imageBaseUrl: String) {
        dataStore.edit { imageStoreBaseurl ->
            imageStoreBaseurl[BASE_URL_PREFERENCE] = imageBaseUrl
        }
    }

    override fun apiKey(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val api_key =
                    preferences[USER_API_KEY_PREFERENCE] ?: "e620691df09a09e70058468621b6e1e6"
                api_key
            }
    }

    override suspend fun saveApiKey(apiKey: String) {
        dataStore.edit { api_key ->
            api_key[USER_API_KEY_PREFERENCE] = apiKey
        }
    }

}
