package com.example.data.repository.movies

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.data.DataConstants.LANG_ENG
import com.example.data.DataConstants.NETWORK_PAGE_SIZE
import com.example.data.dto.dashboard.GenreResponse
import com.example.data.dto.dashboard.MovieItem
import com.example.data.dto.dashboard.MoviePoster
import com.example.data.dto.dashboard.ResponseSearch
import com.example.data.dto.detail.CastItem
import com.example.data.network.NetworkService
import com.example.data.network.Resource
import com.example.data.paging.source.LoadPopularMoviesPagingSource
import com.example.data.paging.source.SimilarMoviesPagingSource
import com.example.data.paging.source.UpComingMoviesPagingSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val apiService: NetworkService):
    MovieRepository {


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
                query = query,
                language = LANG_ENG,
                page = 1
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