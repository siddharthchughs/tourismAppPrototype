package com.example.myweatherapp.settings

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

private val USER_ONBOARDING_PREFERENCE = booleanPreferencesKey("onBoardPreferences")
private val BASE_URL_PREFERENCE = stringPreferencesKey("baseUrl")
private val USER_TOKEN_PREFERENCE = stringPreferencesKey("token")
private val USER_API_KEY_PREFERENCE = stringPreferencesKey("api_key")

interface ApplicationSetting {
    fun token(): Flow<String>
    suspend fun saveToken(storeToken: String)
    fun apiKey(): Flow<String>
    suspend fun saveApiKey(apiKey: String)
    fun getBaseUrl(): Flow<String>
    suspend fun saveBaseUrl(baseUrl: String)
    fun getBoardingOnce(): Flow<Boolean>
    suspend fun saveBoarding(isOnBoardCompleted: Boolean)

}

class ApplicationSettingImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ApplicationSetting {
    override fun token(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val token = preferences[USER_TOKEN_PREFERENCE] ?: ""
                token
            }
    }

    override suspend fun saveToken(storeToken: String) {
        dataStore.edit { token ->
            token[USER_TOKEN_PREFERENCE] = storeToken
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
                val api_key = preferences[USER_API_KEY_PREFERENCE] ?: ""
                api_key
            }
    }

    override suspend fun saveApiKey(apiKey: String) {
        dataStore.edit { api_key ->
            api_key[USER_API_KEY_PREFERENCE] = apiKey
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
                val baseUrl = preferences[BASE_URL_PREFERENCE]
                    ?: "https://api.openweathermap.org/data/2.5/forecast/"
                baseUrl
            }
    }

    override suspend fun saveBaseUrl(baseUrl: String) {
        dataStore.edit { storeBaseurl ->
            storeBaseurl[BASE_URL_PREFERENCE] = baseUrl
        }
    }

    override fun getBoardingOnce(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                val onBoarding = preferences[USER_ONBOARDING_PREFERENCE] ?: false
                onBoarding
            }
    }

    override suspend fun saveBoarding(isOnBoardCompleted: Boolean) {
        dataStore.edit { preference ->
            preference[USER_ONBOARDING_PREFERENCE] = isOnBoardCompleted
        }
    }

}
