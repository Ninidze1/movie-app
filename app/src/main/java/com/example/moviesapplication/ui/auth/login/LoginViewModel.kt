package com.example.moviesapplication.ui.auth.login

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
class LoginViewModel @Inject constructor(private val auth: FirebaseRepository) : ViewModel() {

    private var _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    fun login(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                auth.signInUser(email, password).addOnCompleteListener { process ->
                    if (process.isSuccessful)
                        _loginStatus.postValue(true)
                    else
                        _loginStatus.postValue(false)
                }
            }
        }
    }

}