package com.pollux.moviesmvvm.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val averageRating: Float,
    val storyline: String,
    val actors: List<String>,
    val imdbRating: String? = null,
    @SerializedName("posterurl") val posterUrl: String,
    val isBookmarked: Boolean = false
) : Parcelable

@Parcelize
@Entity(tableName = "movies")
data class MoviesDto(
    @PrimaryKey val id: String,
    val title: String,
    val year: String,
    val genres: String? = null,
    val ratings: String? = null,
    val poster: String,
    val contentRating: String? = null,
    val duration: String? = null,
    val releaseDate: String,
    val originalTitle: String? = null,
    val averageRating: Float,
    val storyline: String,
    val actors: String? = null,
    val imdbRating: String? = null,
    val posterUrl: String,
    val isBookmarked: Boolean = false
) : Parcelable