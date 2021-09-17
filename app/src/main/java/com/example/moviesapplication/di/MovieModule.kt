package com.example.moviesapplication.di

import com.example.moviesapplication.repository.movies.MovieRepository
import com.example.moviesapplication.repository.movies.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class MovieModule {

    @Binds
    @Singleton
    abstract fun provideMoviesRepository(movieRepo: MovieRepositoryImpl): MovieRepository

}