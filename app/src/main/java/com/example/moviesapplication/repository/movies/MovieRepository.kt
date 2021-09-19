package com.example.moviesapplication.repository.movies

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviesapplication.entity.*
import com.example.moviesapplication.network.Resource

interface MovieRepository {
    fun getPopularMovies(): LiveData<PagingData<MovieItem>>
    fun getLatestMovies(): LiveData<PagingData<MoviePoster>>
    fun getSimilarMovies(movieId: Int): LiveData<PagingData<MoviePoster>>

    suspend fun getGenres(): Resource<GenreResponse>
    suspend fun getMovieDetails(movieId: Int): Resource<MovieItem>
    suspend fun searchMovie(query: String): Resource<ResponseSearch>
    suspend fun getActors(movieId: Int): Resource<CastItem>
}