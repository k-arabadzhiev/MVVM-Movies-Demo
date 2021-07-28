package com.pollux.moviesmvvm.view.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.transition.MaterialContainerTransform
import com.pollux.moviesmvvm.R
import com.pollux.moviesmvvm.databinding.FragmentDetailsBinding
import com.pollux.moviesmvvm.model.data.Movies
import com.pollux.moviesmvvm.utils.C
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 450L
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 350L
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val movie = args.movie
            bindMovie(this, movie)
        }
    }

    private fun bindMovie(binding: FragmentDetailsBinding, movie: Movies?) {
        binding.apply {
            movieTitle.text = movie?.title
            if (movie?.originalTitle.isNullOrEmpty()) {
                movieOriginalTitle.visibility = View.GONE
            } else {
                movieOriginalTitle.visibility = View.VISIBLE
                movieOriginalTitle.text = movie?.originalTitle
            }

            if (movie?.duration.isNullOrEmpty()) {
                movieRunningTime.text = C.NOT_AVAILABLE
            } else movieRunningTime.text =
                movie?.duration?.removePrefix("PT")?.replace("M", " min")

            var imdbRating = C.NOT_AVAILABLE
            if (movie!!.imdbRating.isNullOrEmpty()) {
                imdbRating += C.MAX_RATING
                movieRating.text = imdbRating
            }
            else {
                imdbRating = movie.imdbRating + C.MAX_RATING
                movieRating.text = imdbRating
            }

            Glide.with(this@DetailsFragment)
                .load(movie.posterUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(itemPosterPost)
            val yearText = "(" + movie.year + ")"
            movieYear.text = yearText
            arText.text = movie.ratings.average().toString().substring(0, 3)
            crText.text = movie.contentRating
            summaryText.text = movie.storyline
            if(movie.actors.isNullOrEmpty()) {
                actorsText.text = C.NOT_AVAILABLE
            } else {
                actorsText.text = movie.actors.joinToString(", ")
            }
            if(movie.releaseDate.isEmpty()) {
                //Fallback to year
                rdText.text = movie.year
            } else {
                rdText.text = movie.releaseDate
            }
            genresText.text = movie.genres.joinToString(", ")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}