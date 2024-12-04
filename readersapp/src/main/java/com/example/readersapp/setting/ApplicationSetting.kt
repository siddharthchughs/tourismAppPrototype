package com.example.readersapp.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val BASE_URL_PREFERENCE = stringPreferencesKey("baseurl")
private val USER_NAME_PREFERENCE = stringPreferencesKey("name")
private val USER_AUTH_PREFERENCE = stringPreferencesKey("auth")

interface ApplicationSetting {
    fun authorization(): Flow<String>
    suspend fun saveAuthorization(storeAuth: String)
    fun getUsername(): Flow<String>
    suspend fun saveUsername(usernameStore: String)
    fun getBaseUrl(): Flow<String>
    suspend fun saveBaseUrl(baseUrl: String)
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

    override fun getUsername(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val username = preferences[USER_NAME_PREFERENCE] ?: "Unknown user"
                username
            }
    }

    override suspend fun saveUsername(usernameStore: String) {
        dataStore.edit { storeBaseurl ->
            storeBaseurl[USER_NAME_PREFERENCE] = usernameStore
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
                val baseUrl = preferences[BASE_URL_PREFERENCE] ?: "https://www.googleapis.com/books/"
                baseUrl
            }
    }

    override suspend fun saveBaseUrl(baseUrl: String) {
        dataStore.edit { storeBaseurl ->
            storeBaseurl[BASE_URL_PREFERENCE] = baseUrl
        }
    }
}
