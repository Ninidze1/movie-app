package com.example.moviesapplication.di

import com.example.moviesapplication.user_state.DataStoreImpl
import com.example.moviesapplication.repository.datastore.DataStoreInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {
    @Binds
    abstract fun bindDataStore(dataStoreImpl: DataStoreImpl): DataStoreInterface
}