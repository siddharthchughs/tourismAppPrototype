package com.example.readersapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.readersapp.createuser.CreateUserRepository
import com.example.readersapp.createuser.CreateUserRepositoryImpl
import com.example.readersapp.search.SearchBookNetworkDataSourceImpl
import com.example.readersapp.search.SearchBookRepository
import com.example.readersapp.search.SearchBooksNetworkDataSource
import com.example.readersapp.search.earchBookRepositoryImpl
import com.example.readersapp.setting.ApplicationSetting
import com.example.readersapp.setting.ApplicationSettingImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "POCH_FILENAME")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}


@Module
@InstallIn(SingletonComponent::class)
abstract class SearchBooks {
    @Binds
    abstract fun bindSearchBooksNetworkDataSource(searchBookNetworkDataSourceImpl: SearchBookNetworkDataSourceImpl): SearchBooksNetworkDataSource

    @Binds
    abstract fun bindSearchBooksRespository(searchBookRepositoryImpl: earchBookRepositoryImpl): SearchBookRepository
}

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
    @Singleton
    @Provides
    fun bindDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationSettingModule {
    @Binds
    abstract fun bindMovieApplicationSetting(applicationSettingImpl: ApplicationSettingImpl): ApplicationSetting
}

@Module
@InstallIn(SingletonComponent::class)
abstract class CreateUserRepositoryModule {
    @Binds
    abstract fun bindCreateUserRepository(createUserRepositoryImpl: CreateUserRepositoryImpl): CreateUserRepository
}

