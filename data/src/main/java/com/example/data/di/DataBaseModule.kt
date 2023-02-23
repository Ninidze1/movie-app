package com.example.data.di

import com.example.data.db.AppDatabase
import com.example.data.db.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

}