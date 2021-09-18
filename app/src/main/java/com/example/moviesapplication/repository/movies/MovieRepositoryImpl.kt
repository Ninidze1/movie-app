package com.example.moviesapplication.repository.movies

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.moviesapplication.entity.GenreResponse
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.entity.SearchResponse
import com.example.moviesapplication.network.NetworkService
import com.example.moviesapplication.network.Resource
import com.example.moviesapplication.paging.source.LoadMoviesPagingSource
import com.example.moviesapplication.utils.Constants.LANG_ENG
import com.example.moviesapplication.utils.Constants.NETWORK_PAGE_SIZE
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val apiService: NetworkService): MovieRepository {

    override fun getPopularMovies(): LiveData<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = { LoadMoviesPagingSource(apiService) }
        ).liveData
    }

    override fun getLatestMovies(): LiveData<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { LoadMoviesPagingSource(apiService) }
        ).liveData
    }

    override suspend fun getGenres(): Resource<GenreResponse> {
        return try {
            val response = apiService.getGenres()
            if (response.isSuccessful) {
                Resource.success(response.body()!!)
            } else {
                Resource.error(response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }

    override suspend fun searchMovie(query: String): Resource<SearchResponse> {
        return try {
            val response = apiService.searchMovie(
                query,
                LANG_ENG,
                1
            )
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