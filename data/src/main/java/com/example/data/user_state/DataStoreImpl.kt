package com.example.data.user_state

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.DataConstants.SESSION
import com.example.data.DataConstants.SESSION_PREF
import com.example.data.repository.datastore.DataStoreInterface
import com.example.data.user_state.DataStoreImpl.KEYS.SESSION_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SESSION, produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, SESSION_PREF))
    }
)

@Singleton
class DataStoreImpl @Inject constructor(private val dataStore: DataStore<Preferences>):
    DataStoreInterface {

    override fun checkSession(): Flow<Boolean> = dataStore.data.map { value ->
        value[SESSION_KEY] ?: false
    }

    override suspend fun saveSession() {
        dataStore.edit { value ->
            value[SESSION_KEY] = true
        }
    }

    override suspend fun deleteSession() {
        dataStore.edit { value ->
            value[SESSION_KEY] = false
        }
    }
    private object KEYS {
        val SESSION_KEY = booleanPreferencesKey("session_key")

    }
}