package com.example.moviesapplication.room

import androidx.room.*
import com.example.moviesapplication.entity.person.FavMovie

@Dao
interface MovieDao {
    @Query("SELECT * FROM favmovie")
    suspend fun getAll(): List<FavMovie>

    @Query("SELECT movieId FROM favmovie")
    suspend fun getAllIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insert(movie: FavMovie)

    @Query("DELETE FROM favmovie WHERE movieId=:movieId")
    suspend fun deleteById(movieId: Int)

    @Delete
    suspend fun delete(movie: FavMovie)

    @Query("DELETE FROM favmovie")
    suspend fun deleteAll()
}