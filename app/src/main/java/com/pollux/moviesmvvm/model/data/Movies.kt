package com.pollux.moviesmvvm.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movies(
    val id: String,
    val title: String,
    val year: String,
    val genres: List<String>,
    val ratings: List<Int>,
    val poster: String,
    val contentRating: String? = null,
    val duration: String? = null,
    val releaseDate: String,
    val originalTitle: String? = null,
    val averageRating: Int,
    val storyline: String,
    val actors: List<String>,
    val imdbRating: String? = null,
    @SerializedName("posterurl") val posterUrl: String
) : Parcelable
