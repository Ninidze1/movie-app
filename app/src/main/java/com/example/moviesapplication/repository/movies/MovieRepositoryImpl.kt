package com.example.moviesapplication.repository.movies

import com.example.moviesapplication.entity.ResponseItems
import com.example.moviesapplication.network.MovieService
import com.example.moviesapplication.network.Resource
import com.example.moviesapplication.utils.Constants.LANG_ENG
import com.example.moviesapplication.utils.Constants.NETWORK_PAGE_SIZE
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val apiService: MovieService): MovieRepository {
    override suspend fun getPopularMovies(): Resource<ResponseItems> {
                return try {
            val response = apiService.getPopularMovies(LANG_ENG, NETWORK_PAGE_SIZE)
            if (response.isSuccessful) {
                Resource.success(response.body()!!)
            } else {
                Resource.error(response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }
}