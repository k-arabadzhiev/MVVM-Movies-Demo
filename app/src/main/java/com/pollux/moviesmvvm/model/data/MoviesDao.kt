package com.pollux.moviesmvvm.model.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MoviesDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MoviesDto>)

    @Update
    suspend fun updateMovie(movie: MoviesDto)

    @Query("SELECT * FROM movies WHERE isBookmarked = 1")
    fun getAllBookmarkedMovies(): Flow<List<MoviesDto>>

    @Query("DELETE FROM movies WHERE isBookmarked = 0")
    suspend fun deleteAllMovies()
}