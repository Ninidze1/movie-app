package com.example.moviesapplication.repository.datastore

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRep @Inject constructor(private val dataStore: DataStoreInterface) {
    fun checkSession() : Flow<Boolean> = dataStore.checkSession()
    suspend fun saveSession() = dataStore.saveSession()
    suspend fun deleteSession() = dataStore.deleteSession()
}