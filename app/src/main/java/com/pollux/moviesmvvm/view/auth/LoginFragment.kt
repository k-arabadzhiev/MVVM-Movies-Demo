package com.pollux.moviesmvvm.view.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.pollux.moviesmvvm.R
import com.pollux.moviesmvvm.databinding.FragmentLoginBinding
import com.pollux.moviesmvvm.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by activityViewModels()

    private lateinit var navController: NavController

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        navController = findNavController()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.homeFragment, false)
        }

        binding.loginButton.setOnClickListener {
            loginUser(view)
        }

        viewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                is LoginViewModel.AuthenticationState.Authenticated -> navController.popBackStack()
                is LoginViewModel.AuthenticationState.InvalidAuthentication -> Snackbar.make(
                    view, "Login unsuccessful",
                    Snackbar.LENGTH_LONG
                ).show()
                else -> Snackbar.make(
                    view, "You need to login in order to use watch list",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun loginUser(view: View) {
        val email = binding.usernameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Snackbar.make(
                            view, e.message.toString(), Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}