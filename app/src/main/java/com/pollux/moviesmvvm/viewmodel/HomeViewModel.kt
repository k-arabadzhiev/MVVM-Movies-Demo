package com.pollux.moviesmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pollux.moviesmvvm.model.MoviesRepository
import com.pollux.moviesmvvm.model.data.Movies
import com.pollux.moviesmvvm.utils.C
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val moviesLiveData = MutableLiveData<List<Movies>>()
    val movies: LiveData<List<Movies>> = moviesLiveData

    private val _response = MutableLiveData<Response<List<Movies>>>()
    val response: LiveData<Response<List<Movies>>> = _response

    init {
        viewModelScope.launch {
            val response = repository.fetchMovies(C.TOKEN)
            _response.value = response

            val movies = response.body()!!
            moviesLiveData.value = movies
        }
    }

}