package com.example.moviesapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapplication.entity.User

@Database(entities = [User::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}