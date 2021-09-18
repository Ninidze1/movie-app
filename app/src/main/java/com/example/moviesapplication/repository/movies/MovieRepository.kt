package com.example.moviesapplication.repository.movies

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviesapplication.entity.GenreResponse
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.entity.SearchResponse
import com.example.moviesapplication.network.Resource

interface MovieRepository {
    fun getPopularMovies(): LiveData<PagingData<MovieItem>>
    fun getLatestMovies(): LiveData<PagingData<MovieItem>>

    suspend fun getGenres(): Resource<GenreResponse>
    suspend fun searchMovie(query: String): Resource<SearchResponse>
}