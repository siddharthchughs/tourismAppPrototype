package com.example.movieapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.movieapp.currentPlayedDetail.CurrentMovieDetailNetworkDataSource
import com.example.movieapp.currentPlayedDetail.CurrentMovieDetailNetworkDataSourceImpl
import com.example.movieapp.currentPlayedDetail.CurrentMovieDetailRepository
import com.example.movieapp.currentPlayedDetail.CurrentMovieDetailRepositoryImpl
import com.example.movieapp.detail.MovieDetailNetworkDataSource
import com.example.movieapp.detail.MovieDetailNetworkDataSourceImpl
import com.example.movieapp.detail.MovieDetailRepository
import com.example.movieapp.detail.MovieDetailRepositoryImpl
import com.example.movieapp.currentmovies.CurrentMovieNetworkDataSource
import com.example.movieapp.currentmovies.CurrentMovieNetworkDataSourceImpl
import com.example.movieapp.currentmovies.CurrentMovieRepository
import com.example.movieapp.currentmovies.CurrentMovieRepositoryImpl
import com.example.movieapp.movie.MovieNetworkDataSource
import com.example.movieapp.movie.MovieNetworkDataSourceImpl
import com.example.movieapp.movie.MovieRepository
import com.example.movieapp.movie.MovieRepositoryImpl
import com.example.movieapp.movie.MoviesRepository
import com.example.movieapp.movie.MoviesRepositoryImpl
import com.example.movieapp.settings.ApplicationSetting
import com.example.movieapp.settings.ApplicationSettingImpl
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
abstract class MovieModule {
    @Binds
    abstract fun bindMovieNetworkDataSource(movieNetworkDataSourceImpl: MovieNetworkDataSourceImpl): MovieNetworkDataSource

    @Binds
    abstract fun bindCurrentMovieNetworkDataSource(currentmovieNetworkDataSourceImpl: CurrentMovieNetworkDataSourceImpl): CurrentMovieNetworkDataSource

    @Binds
    abstract fun bindMovieDetailNetworkDataSource(movieDetailNetworkDataSourceImpl: MovieDetailNetworkDataSourceImpl): MovieDetailNetworkDataSource

    @Binds
    abstract fun bindMovieDetailRepository(movieDetailRepositoryImpl: MovieDetailRepositoryImpl): MovieDetailRepository

    @Binds
    abstract fun bindCurrentMovieRepository(currentmovieRepositoryImpl: CurrentMovieRepositoryImpl): CurrentMovieRepository

    @Binds
    abstract fun bindCurrentMovieRepository(currentmovieNetworkDataSourceImpl: CurrentMovieDetailNetworkDataSourceImpl): CurrentMovieDetailNetworkDataSource

    @Binds
    abstract fun bindCurrentMovieDetailRepository(currentmovieDetailRepositoryImpl: CurrentMovieDetailRepositoryImpl): CurrentMovieDetailRepository
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

@InstallIn(SingletonComponent::class) // Assuming you're using Hilt
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationSettingModule {
    @Binds
    abstract fun bindMovieApplicationSetting(applicationSettingImpl: ApplicationSettingImpl): ApplicationSetting
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesRespositoryModule {
    @Binds
    abstract fun bindMovieRepositoryImpl(moviesRespositoryImpl: MoviesRepositoryImpl): MoviesRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {
    @Binds
    abstract fun bindingMovieovieRepositoryImpl(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}
