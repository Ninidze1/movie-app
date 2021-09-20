package com.example.moviesapplication.repository.room

import com.example.moviesapplication.entity.person.FavMovie
import com.example.moviesapplication.room.MovieDao
import javax.inject.Inject

class RoomRepository @Inject constructor(private val movieDao: MovieDao ) {

    suspend fun getAll(): List<FavMovie> = movieDao.getAll()

    suspend  fun insert(movie: FavMovie) = movieDao.insert(movie)

    suspend fun deleteById(movieId: Int) = movieDao.deleteById(movieId)

    suspend fun delete(movie: FavMovie) = movieDao.delete(movie)

    suspend fun getAllIds(): List<Int> = movieDao.getAllIds()



}