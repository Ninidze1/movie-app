package com.example.moviesapplication.di

import com.example.moviesapplication.repository.movies.MovieRepository
import com.example.moviesapplication.repository.movies.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MovieModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(movieRepo: MovieRepositoryImpl): MovieRepository =
        movieRepo

}