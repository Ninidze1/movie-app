package com.example.moviesapplication.screens.bottom_nav.profile

import androidx.lifecycle.ViewModel
import com.example.moviesapplication.repository.firebase.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val auth: FirebaseRepository) : ViewModel() {

}