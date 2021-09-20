package com.example.moviesapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapplication.entity.person.FavMovie

@Database(entities = [FavMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}