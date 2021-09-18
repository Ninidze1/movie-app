package com.example.moviesapplication.screens.single_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.network.Resource
import com.example.moviesapplication.repository.movies.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SingleMovieViewModel @Inject constructor(private val movieRep: MovieRepository) : ViewModel() {

    private var _movieDetails = MutableLiveData<Resource<MovieItem>>()
    val movieDetails: LiveData<Resource<MovieItem>> = _movieDetails

    fun similarMovies(movieId: Int): LiveData<PagingData<MovieItem>> {
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
}