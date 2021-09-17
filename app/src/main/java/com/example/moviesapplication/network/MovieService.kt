package com.example.moviesapplication.network

import com.example.moviesapplication.entity.ResponseItems
import com.example.moviesapplication.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("tv/popular?api_key=$API_KEY")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<ResponseItems>
}