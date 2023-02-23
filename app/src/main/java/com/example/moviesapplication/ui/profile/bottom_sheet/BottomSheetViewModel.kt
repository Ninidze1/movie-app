package com.example.moviesapplication.ui.profile.bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.datastore.DataStoreRep
import com.example.data.repository.firebase.FirebaseRepository
import com.example.data.repository.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val dataStore: DataStoreRep,
    private val room: RoomRepository,
    private val firebaseRepository: FirebaseRepository
    ): ViewModel() {

    fun signOut() = firebaseRepository.signOut()

    fun deleteSession() {
        viewModelScope.launch {
            dataStore.deleteSession()
        }
    }

    fun clearFavourites() {
        viewModelScope.launch {
            room.deleteAll()
        }
    }
}