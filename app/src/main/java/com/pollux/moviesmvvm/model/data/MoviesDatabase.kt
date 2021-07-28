package com.pollux.moviesmvvm.model.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoviesDto::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}