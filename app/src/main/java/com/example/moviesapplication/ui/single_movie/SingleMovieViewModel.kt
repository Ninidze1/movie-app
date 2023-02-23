package com.example.moviesapplication.ui.single_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.dto.dashboard.MovieItem
import com.example.data.dto.dashboard.MoviePoster
import com.example.data.dto.detail.CastItem
import com.example.data.dto.person.FavMovie
import com.example.data.network.Resource
import com.example.data.repository.movies.MovieRepository
import com.example.data.repository.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SingleMovieViewModel @Inject constructor(
    private val movieRep: MovieRepository,
    private val room: RoomRepository
) : ViewModel() {

    private var _movieDetails = MutableLiveData<Resource<MovieItem>>()
    val movieDetails: LiveData<Resource<MovieItem>> = _movieDetails

    private var _allIds = MutableLiveData<List<Int>>()
    val allIds: LiveData<List<Int>> = _allIds


    private var _movieCast = MutableLiveData<Resource<CastItem>>()
    val movieCast: LiveData<Resource<CastItem>> = _movieCast

    fun similarMovies(movieId: Int): LiveData<PagingData<MoviePoster>> {
        return movieRep.getSimilarMovies(movieId).cachedIn(viewModelScope)
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val result = movieRep.getMovieDetails(movieId)
                _movieDetails.postValue(result)
            }
        }
    }

    fun getMovieActors(movieId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val result = movieRep.getActors(movieId)
                _movieCast.postValue(result)
            }
        }
    }

    fun addToFav(movie: FavMovie) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                room.insert(movie)
            }
        }
    }

    fun removeFromFav(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                room.deleteById(id)
            }
        }
    }

    suspend fun getAllIds(): List<Int> {
        _allIds.postValue(room.getAllIds())
        return room.getAllIds()
    }

}