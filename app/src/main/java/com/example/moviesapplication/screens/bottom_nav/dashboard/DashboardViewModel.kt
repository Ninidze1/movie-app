package com.example.moviesapplication.screens.bottom_nav.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.network.Resource
import com.example.moviesapplication.repository.movies.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val movieRep: MovieRepository
    ) : ViewModel() {

    private var _popularMovies = MutableLiveData<Resource<List<MovieItem>>>()
    val popularMovies: LiveData<Resource<List<MovieItem>>> = _popularMovies


    fun getPopularMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val result = movieRep.getPopularMovies()
                _popularMovies.postValue(result)
            }
        }
    }
}