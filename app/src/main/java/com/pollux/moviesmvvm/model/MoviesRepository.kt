package com.pollux.moviesmvvm.model

import com.pollux.moviesmvvm.model.network.MovieApiService
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: MovieApiService
) {
    suspend fun fetchMovies(token: String) = api.getMovies(token)
}