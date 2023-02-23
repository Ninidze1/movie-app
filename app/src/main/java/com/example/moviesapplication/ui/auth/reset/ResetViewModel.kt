package com.example.moviesapplication.ui.auth.reset

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
class ResetViewModel @Inject constructor(private val auth: FirebaseRepository) : ViewModel() {

    private var _resetStatus = MutableLiveData<Boolean>()
    val resetStatus: LiveData<Boolean> = _resetStatus

    fun resetPassword(email: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                auth.resetUser(email).addOnCompleteListener { process ->
                    if (process.isSuccessful)
                        _resetStatus.postValue(true)
                    else
                        _resetStatus.postValue(false)
                }
            }
        }
    }
}