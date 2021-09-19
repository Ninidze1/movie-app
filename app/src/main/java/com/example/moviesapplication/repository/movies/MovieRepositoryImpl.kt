package com.example.moviesapplication.repository.movies

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.moviesapplication.entity.*
import com.example.moviesapplication.network.NetworkService
import com.example.moviesapplication.network.Resource
import com.example.moviesapplication.paging.source.LoadPopularMoviesPagingSource
import com.example.moviesapplication.paging.source.SimilarMoviesPagingSource
import com.example.moviesapplication.paging.source.UpComingMoviesPagingSource
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
            pagingSourceFactory = { LoadPopularMoviesPagingSource(apiService) }
        ).liveData
    }

    override fun getLatestMovies(): LiveData<PagingData<MoviePoster>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { UpComingMoviesPagingSource(apiService) }
        ).liveData
    }

    override fun getSimilarMovies(movieId: Int): LiveData<PagingData<MoviePoster>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { SimilarMoviesPagingSource(apiService, movieId) }
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

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieItem> {
        return try {
            val response = apiService.getMovieDetails(movieId)
            if (response.isSuccessful) {
                Resource.success(response.body()!!)
            } else {
                Resource.error(response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }

    override suspend fun searchMovie(query: String): Resource<ResponseSearch> {
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

    override suspend fun getActors(movieId: Int): Resource<CastItem> {
        return try {
            val response = apiService.getActors(movieId)
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