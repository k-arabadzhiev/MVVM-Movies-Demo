package com.pollux.moviesmvvm.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.pollux.moviesmvvm.databinding.ItemPosterBinding
import com.pollux.moviesmvvm.model.data.Movies
import com.pollux.moviesmvvm.utils.C

class MoviesAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Movies, MoviesAdapter.MoviesViewHolder>(MovieComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ItemPosterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class MoviesViewHolder(private val binding: ItemPosterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(binding.itemContainer, item)
                    }
                }
            }
        }

        fun bind(movie: Movies) {
            binding.apply {
                movieTitle.text = movie.title
                if (movie.originalTitle.isNullOrEmpty()) {
                    movieOriginalTitle.visibility = View.GONE
                } else {
                    movieOriginalTitle.visibility = View.VISIBLE
                    movieOriginalTitle.text = movie.originalTitle
                }

                if (movie.duration.isNullOrEmpty()) {
                    movieRunningTime.text = C.NOT_AVAILABLE
                } else movieRunningTime.text =
                    movie.duration.removePrefix("PT").replace("M", " min")

                var rating = C.NOT_AVAILABLE
                if (movie.imdbRating.isNullOrEmpty()) {
                    rating += C.MAX_RATING
                    movieRating.text = rating
                }
                else {
                    rating = movie.imdbRating + C.MAX_RATING
                    movieRating.text = rating
                }
                val yearText = "(" + movie.year + ")"
                movieYear.text = yearText

                Glide.with(itemView)
                    .load(movie.posterUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(itemPosterPost)
                itemContainer.transitionName = "transition_${movie.title}"
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(cardView: View, movie: Movies)
    }

    class MovieComparator : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movies, newItem: Movies) =
            oldItem == newItem
    }

}