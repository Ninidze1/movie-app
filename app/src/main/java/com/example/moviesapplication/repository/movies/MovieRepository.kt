package com.example.moviesapplication.repository.movies

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviesapplication.entity.GenreResponse
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.entity.MoviePoster
import com.example.moviesapplication.entity.ResponseSearch
import com.example.moviesapplication.network.Resource

interface MovieRepository {
    fun getPopularMovies(): LiveData<PagingData<MovieItem>>
    fun getLatestMovies(): LiveData<PagingData<MoviePoster>>
    fun getSimilarMovies(movieId: Int): LiveData<PagingData<MoviePoster>>

    suspend fun getGenres(): Resource<GenreResponse>
    suspend fun getMovieDetails(movieId: Int): Resource<MovieItem>
    suspend fun searchMovie(query: String): Resource<ResponseSearch>
}