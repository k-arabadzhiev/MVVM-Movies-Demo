package com.pollux.moviesmvvm.model.network

import com.pollux.moviesmvvm.model.data.Movies
import retrofit2.http.GET

interface MovieApiService {

    @GET("FEND16/movie-json-data/master/json/movies-coming-soon.json")
    suspend fun getMovies() : List<Movies>
}