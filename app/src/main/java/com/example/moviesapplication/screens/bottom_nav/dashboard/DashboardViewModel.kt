package com.example.moviesapplication.screens.bottom_nav.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesapplication.entity.GenreResponse
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.entity.SearchResponse
import com.example.moviesapplication.network.Resource
import com.example.moviesapplication.repository.movies.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val movieRep: MovieRepository
    ) : ViewModel() {

    fun popularMovies(): LiveData<PagingData<MovieItem>> {
        return movieRep.getPopularMovies().cachedIn(viewModelScope)
    }

    fun upComingMovies(): LiveData<PagingData<MovieItem>> {
        return movieRep.getLatestMovies().cachedIn(viewModelScope)
    }

    private var _searchResult = MutableLiveData<Resource<SearchResponse>>()
    val searchResult: LiveData<Resource<SearchResponse>> = _searchResult

//
//    private var _moviesByGenre = MutableLiveData<Resource<ResponseItems>>()
//    val moviesByGenre: LiveData<Resource<ResponseItems>> = _moviesByGenre

    private var _genres = MutableLiveData<Resource<GenreResponse>>()
    val genres: LiveData<Resource<GenreResponse>> = _genres

//    fun getMoviesByGenre() {
//        viewModelScope.launch {
//            withContext(Dispatchers.Default) {
//                val result = movieRep.getPopularMovies()
//                _moviesByGenre.postValue(result)
//            }
//        }
//    }

    fun getGenres() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val result = movieRep.getGenres()
                _genres.postValue(result)
            }
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val result = movieRep.searchMovie(query)
                delay(500)
                _searchResult.postValue(result)
            }
        }
    }
}