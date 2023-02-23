package com.example.data.repository.movies

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.data.dto.dashboard.GenreResponse
import com.example.data.dto.dashboard.MovieItem
import com.example.data.dto.dashboard.MoviePoster
import com.example.data.dto.dashboard.ResponseSearch
import com.example.data.dto.detail.CastItem
import com.example.data.network.Resource

interface MovieRepository {
    fun getPopularMovies(): LiveData<PagingData<MovieItem>>
    fun getLatestMovies(): LiveData<PagingData<MoviePoster>>
    fun getSimilarMovies(movieId: Int): LiveData<PagingData<MoviePoster>>

    suspend fun getGenres(): Resource<GenreResponse>
    suspend fun getMovieDetails(movieId: Int): Resource<MovieItem>
    suspend fun searchMovie(query: String): Resource<ResponseSearch>
    suspend fun getActors(movieId: Int): Resource<CastItem>
}