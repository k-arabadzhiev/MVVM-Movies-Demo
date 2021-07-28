package com.pollux.moviesmvvm.viewmodel

import androidx.lifecycle.*
import com.pollux.moviesmvvm.model.MoviesRepository
import com.pollux.moviesmvvm.model.data.Movies
import com.pollux.moviesmvvm.model.data.MoviesDto
import com.pollux.moviesmvvm.utils.C
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    //private val moviesLiveData = MutableLiveData<List<MoviesDto>>()
    //val movies: LiveData<List<MoviesDto>> = moviesLiveData

    private val _response = MutableLiveData<Response<List<Movies>>>()
    val response: LiveData<Response<List<Movies>>> = _response

    val movies = repository.getMovies(C.TOKEN).asLiveData()

    /*init {
        viewModelScope.launch {
            val response = repository.fetchMovies(C.TOKEN)
            _response.value = response

            val movies = response.body()!!.map {
                movieToMovieDto(it, it.isBookmarked)
            }
            moviesLiveData.value = movies
            repository.saveMovies(movies)
        }
    }*/

    fun onBookmarkClick(movie: MoviesDto) {
        val currentlyBookmarked = movie.isBookmarked
        val updatedMovie = movie.copy(isBookmarked = !currentlyBookmarked)
        viewModelScope.launch {
            repository.updateMovie(updatedMovie)
        }
    }
}