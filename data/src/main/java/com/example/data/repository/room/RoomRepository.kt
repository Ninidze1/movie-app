package com.example.data.repository.room

import com.example.data.db.MovieDao
import com.example.data.dto.person.FavMovie
import javax.inject.Inject

class RoomRepository @Inject constructor(private val movieDao: MovieDao) {

    suspend fun getAll(): List<FavMovie> = movieDao.getAll()

    suspend  fun insert(movie: FavMovie) = movieDao.insert(movie)

    suspend fun deleteById(movieId: Int) = movieDao.deleteById(movieId)

    suspend fun getAllIds(): List<Int> = movieDao.getAllIds()

    suspend fun deleteAll() = movieDao.deleteAll()



}