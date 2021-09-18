package com.example.moviesapplication.network

import com.example.moviesapplication.entity.GenreResponse
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.entity.ResponseItems
import com.example.moviesapplication.entity.SearchResponse
import com.example.moviesapplication.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<ResponseItems<MovieItem>>

    @GET("movie/upcoming?api_key=$API_KEY")
    suspend fun getUpComingMovies(
        @Query("page") page: Int
    ): Response<ResponseItems<MovieItem>>

    @GET("movie/{movie_id}/similar?api_key=$API_KEY")
    suspend fun getSimilarMovies(
        @Path("movie_id") movie_id: Int,
        @Query("page") page: Int
    ): Response<ResponseItems<MovieItem>>

    @GET("movie/{movie_id}?api_key=$API_KEY")
    suspend fun getMovieDetails(@Path("movie_id") movie_id: Int): Response<MovieItem>

    @GET("search/keyword?api_key=$API_KEY")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<SearchResponse>

    @GET("genre/movie/list?api_key=$API_KEY")
    suspend fun getGenres(): Response<GenreResponse>
}