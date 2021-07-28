package com.pollux.moviesmvvm.model

import androidx.room.withTransaction
import com.pollux.moviesmvvm.model.data.MoviesDatabase
import com.pollux.moviesmvvm.model.data.MoviesDto
import com.pollux.moviesmvvm.model.network.MovieApiService
import com.pollux.moviesmvvm.utils.movieToMovieDto
import com.pollux.moviesmvvm.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: MovieApiService,
    private val moviesDb: MoviesDatabase
) {

    private val moviesDao = moviesDb.moviesDao()

    fun getMovies(token: String) = networkBoundResource(
        query = {
            moviesDao.getAllMovies()
        },
        fetch = {
            api.getMovies(token)
        },
        saveFetchResult = { moviesResponse ->
            val movies = moviesResponse.body()!!.map {
                movieToMovieDto(it, it.isBookmarked)
            }
            moviesDb.withTransaction {
                moviesDao.deleteAllMovies()
                moviesDao.insertMovies(movies)
            }
        }
    )

    suspend fun updateMovie(movie: MoviesDto){
        moviesDao.updateMovie(movie)
    }

    fun getAllBookmarkedMovies(): Flow<List<MoviesDto>> =
        moviesDao.getAllBookmarkedMovies()
}