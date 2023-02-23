package com.example.data.repository.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreInterface {
    fun checkSession(): Flow<Boolean>
    suspend fun saveSession()
    suspend fun deleteSession()
}