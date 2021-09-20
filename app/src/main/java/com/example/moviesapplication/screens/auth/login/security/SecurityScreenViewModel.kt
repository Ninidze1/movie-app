package com.example.moviesapplication.screens.auth.login.security

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapplication.repository.datastore.DataStoreRep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurityScreenViewModel @Inject constructor(private val dataStore: DataStoreRep) : ViewModel() {

    val sessionStatus: LiveData<Boolean> = dataStore.checkSession().asLiveData()

    fun saveSession() {
        viewModelScope.launch {
            dataStore.saveSession()
        }
    }

}