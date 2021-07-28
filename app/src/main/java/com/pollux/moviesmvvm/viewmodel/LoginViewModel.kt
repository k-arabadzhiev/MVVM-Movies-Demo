package com.pollux.moviesmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.pollux.moviesmvvm.model.MoviesRepository
import com.pollux.moviesmvvm.model.auth.FirebaseUserLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MoviesRepository
): ViewModel() {

    val authenticationState = FirebaseUserLiveData().map { user ->
        if(user != null){
            AuthenticationState.Authenticated
        } else {
            AuthenticationState.Unathenticated
        }
    }

    sealed class AuthenticationState {
        object Authenticated : AuthenticationState()
        object Unathenticated : AuthenticationState()
        object InvalidAuthentication : AuthenticationState()
    }
}