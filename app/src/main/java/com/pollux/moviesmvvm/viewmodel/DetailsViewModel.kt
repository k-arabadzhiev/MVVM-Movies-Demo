package com.pollux.moviesmvvm.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pollux.moviesmvvm.model.MoviesRepository
import com.pollux.moviesmvvm.model.data.MoviesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    fun onBookmarkClick(movie: MoviesDto) {
        val currentlyBookmarked = movie.isBookmarked
        val updatedMovie = movie.copy(isBookmarked = !currentlyBookmarked)
        viewModelScope.launch {
            repository.updateMovie(updatedMovie)
        }
    }

}