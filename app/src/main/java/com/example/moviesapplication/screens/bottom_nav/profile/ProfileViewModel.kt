package com.example.moviesapplication.screens.bottom_nav.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviesapplication.entity.person.FavMovie
import com.example.moviesapplication.repository.datastore.DataStoreRep
import com.example.moviesapplication.repository.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStore: DataStoreRep, private val room: RoomRepository
) : ViewModel() {

    private var _clickedAmount = MutableLiveData<Int>()
    val clickedAmount: LiveData<Int> = _clickedAmount

    private var clicked = 0

    val sessionStatus: LiveData<Boolean> = dataStore.checkSession().asLiveData()

    suspend fun getAllFavourites(): List<FavMovie> {
        return room.getAll()
    }

    fun developerFeature() {
        if (clicked < 10) {
            clicked++
            _clickedAmount.postValue(clicked)
        }

    }



}