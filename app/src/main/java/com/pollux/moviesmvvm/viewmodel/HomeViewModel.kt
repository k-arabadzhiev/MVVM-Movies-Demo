package com.pollux.moviesmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pollux.moviesmvvm.model.MoviesRepository
import com.pollux.moviesmvvm.model.data.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val moviesLiveData = MutableLiveData<List<Movies>>()
    val movies: LiveData<List<Movies>> = moviesLiveData

    init {
        viewModelScope.launch {
            val movies = repository.fetchMovies()
            moviesLiveData.value = movies
        }
    }

}