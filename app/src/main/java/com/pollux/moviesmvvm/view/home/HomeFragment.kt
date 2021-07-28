package com.pollux.moviesmvvm.view.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.pollux.moviesmvvm.R
import com.pollux.moviesmvvm.databinding.FragmentHomeBinding
import com.pollux.moviesmvvm.model.data.Movies
import com.pollux.moviesmvvm.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeFragment"
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), MoviesAdapter.OnItemClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        val moviesAdapter = MoviesAdapter(this)

        binding.apply {
            recyclerView.apply {
                adapter = moviesAdapter
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }

        viewModel.response.observe(viewLifecycleOwner){ response ->
            if(response.isSuccessful){
                Log.i(TAG, "onViewCreated: ${response.headers()}")
            }
        }
    }


    override fun onItemClick(cardView: View, movie: Movies) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = 450L
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = 350L
        }

        val movieCardTransitionName = "transition_movie_details"
        val extras = FragmentNavigatorExtras(
            cardView to movieCardTransitionName
        )

        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movie, movie.title)
        findNavController().navigate(action, extras)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}