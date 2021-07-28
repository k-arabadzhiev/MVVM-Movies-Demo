package com.pollux.moviesmvvm.model.network

import com.pollux.moviesmvvm.model.data.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MovieApiService {

    @GET("FEND16/movie-json-data/master/json/movies-coming-soon.json")
    suspend fun getMovies(@Header("Authorization") token: String): Response<List<Movies>>
}