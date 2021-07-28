package com.pollux.moviesmvvm.view.watchlist

import com.pollux.moviesmvvm.view.home.MoviesAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.pollux.moviesmvvm.R
import com.pollux.moviesmvvm.databinding.FragmentHomeBinding
import com.pollux.moviesmvvm.model.data.MoviesDto
import com.pollux.moviesmvvm.viewmodel.WatchListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "WatchListFragment"

@AndroidEntryPoint
class WatchListFragment : Fragment(R.layout.fragment_watch_list), MoviesAdapter.OnItemClickListener {

    private val viewModel: WatchListViewModel by viewModels()
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

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.bookmarks.collect {
                    val bookmarks = it ?: return@collect

                    moviesAdapter.submitList(bookmarks)
                }
            }
        }

    }

    override fun onItemClick(cardView: View, movie: MoviesDto) {
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

        val action = WatchListFragmentDirections.actionWatchListFragment2ToDetailsFragment(movie, movie.title)
        findNavController().navigate(action, extras)
    }

    override fun onBookmarkClick(movie: MoviesDto) {
        viewModel.onBookmarkClick(movie)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}