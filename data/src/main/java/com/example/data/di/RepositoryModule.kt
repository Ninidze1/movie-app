package com.example.data.di

import com.example.data.repository.datastore.DataStoreInterface
import com.example.data.repository.movies.MovieRepository
import com.example.data.repository.movies.MovieRepositoryImpl
import com.example.data.user_state.DataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDataStore(dataStoreImpl: DataStoreImpl): DataStoreInterface

    @Binds
    @Singleton
    abstract fun provideMoviesRepository(movieRepo: MovieRepositoryImpl): MovieRepository
}