package com.example.moviesapplication.ui.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.datastore.DataStoreRep
import com.example.data.user_state.EncryptedSharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecurityScreenViewModel @Inject constructor(
    private val dataStore: DataStoreRep,
    private val encryptedSharedPref: EncryptedSharedPref
    ) : ViewModel() {

    fun readPasscode() = encryptedSharedPref.readPasscode()

    fun writePasscode(passcode: List<Int>) = encryptedSharedPref.writePasscode(passcode)

    fun deletePasscode() = encryptedSharedPref.deletePasscode()

    fun saveSession() {
        viewModelScope.launch {
            dataStore.saveSession()
        }
    }

}