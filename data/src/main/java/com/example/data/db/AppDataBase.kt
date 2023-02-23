package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dto.person.FavMovie

@Database(entities = [FavMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}