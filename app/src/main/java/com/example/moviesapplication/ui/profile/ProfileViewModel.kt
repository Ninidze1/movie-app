package com.example.moviesapplication.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.data.dto.person.FavMovie
import com.example.data.repository.datastore.DataStoreRep
import com.example.data.repository.firebase.FirebaseRepository
import com.example.data.repository.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    dataStore: DataStoreRep,
    private val room: RoomRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    val sessionStatus: LiveData<Boolean> = dataStore.checkSession().asLiveData()

    fun isSessionActive() = firebaseRepository.getUserUid() != null

    suspend fun getAllFavourites(): List<FavMovie> {
        return room.getAll()
    }


}