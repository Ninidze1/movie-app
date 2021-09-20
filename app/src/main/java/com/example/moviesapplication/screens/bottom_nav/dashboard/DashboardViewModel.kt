package com.example.moviesapplication.screens.bottom_nav.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesapplication.entity.dashboard.GenreResponse
import com.example.moviesapplication.entity.dashboard.MovieItem
import com.example.moviesapplication.entity.dashboard.MoviePoster
import com.example.moviesapplication.entity.dashboard.ResponseSearch
import com.example.moviesapplication.network.Resource
import com.example.moviesapplication.repository.movies.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val movieRep: MovieRepository
    ) : ViewModel() {

    var job: Job? = null

    private var _searchResult = MutableLiveData<Resource<ResponseSearch>>()
    val searchResult: LiveData<Resource<ResponseSearch>> = _searchResult

    private var _genres = MutableLiveData<Resource<GenreResponse>>()
    val genres: LiveData<Resource<GenreResponse>> = _genres

    fun popularMovies(): LiveData<PagingData<MovieItem>> {
        return movieRep.getPopularMovies().cachedIn(viewModelScope)
    }

    fun upComingMovies(): LiveData<PagingData<MoviePoster>> {
        return movieRep.getLatestMovies().cachedIn(viewModelScope)
    }

    fun getGenres() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val result = movieRep.getGenres()
                _genres.postValue(result)
            }
        }
    }

    fun searchMovie(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val result = movieRep.searchMovie(query)
                delay(500)
                _searchResult.postValue(result)
            }
        }
    }
}