package com.example.moviesapplication.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.firebase.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val auth: FirebaseRepository) : ViewModel() {

    private var _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean> = _registerStatus

    fun register(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                auth.signUpUser(email, password).addOnCompleteListener { process ->
                    if (process.isSuccessful)
                        _registerStatus.postValue(true)
                    else
                        _registerStatus.postValue(false)
                }
            }
        }
    }
}