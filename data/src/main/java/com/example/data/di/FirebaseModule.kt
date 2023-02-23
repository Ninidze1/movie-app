package com.example.data.di

import com.example.data.repository.firebase.FirebaseRepository
import com.example.data.repository.firebase.FirebaseRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module

object FirebaseModule {

    @Provides
    @Singleton
    fun bindAuthDep(auth: FirebaseRepositoryImpl): FirebaseRepository = auth

    @Provides
    @Singleton
    fun provideFireBaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}