package com.example.moviesapplication.repository.movies

import com.example.moviesapplication.entity.ResponseItems
import com.example.moviesapplication.network.Resource

interface MovieRepository {
    suspend fun getPopularMovies(): Resource<ResponseItems>
    suspend fun getLatestMovies(): Resource<ResponseItems>
}