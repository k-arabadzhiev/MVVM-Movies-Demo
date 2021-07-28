package com.pollux.moviesmvvm.utils

import com.pollux.moviesmvvm.model.data.Movies
import com.pollux.moviesmvvm.model.data.MoviesDto

fun movieToMovieDto(movie: Movies, currentlyBookmarked: Boolean) = MoviesDto(
    id = movie.id,
    title = movie.title,
    year = movie.year,
    genres = movie.genres.joinToString(", "),
    ratings = movie.ratings.average().toString().substring(0, 3),
    poster = movie.poster,
    contentRating = movie.contentRating,
    duration = if (movie.duration.isNullOrEmpty()) {
        C.NOT_AVAILABLE
    } else movie.duration.removePrefix("PT").replace("M", " min"),
    releaseDate = movie.releaseDate,
    originalTitle = movie.originalTitle,
    averageRating = movie.averageRating,
    storyline = movie.storyline,
    actors = movie.actors.joinToString(", "),
    imdbRating = movie.imdbRating,
    posterUrl = movie.posterUrl,
    isBookmarked = currentlyBookmarked
)