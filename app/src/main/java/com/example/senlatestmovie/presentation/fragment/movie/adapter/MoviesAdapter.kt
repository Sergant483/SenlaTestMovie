package com.example.senlatestmovie.presentation.fragment.movie.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.databinding.MovieViewBinding
import com.squareup.picasso.Picasso

typealias OnItemClickListener = (position: Int) -> Unit

class MoviesAdapter(
    private val moviesItems: List<MovieModel>,
    private val onItemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MovieViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(moviesItems[position])
    }

    override fun getItemCount(): Int = moviesItems.size

    inner class MoviesViewHolder(private val binding: MovieViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieItem: MovieModel) {
            Picasso.get().load(POSTER_BASE_URL + movieItem.poster_path).into(binding.posterImage)
            binding.description.text = movieItem.overview
            binding.releaseDate.text = movieItem.release_date
            binding.country.text = movieItem.country
            movieItem.link = MOVIE_BASE_URL + movieItem.id
            binding.link.text = movieItem.link
            binding.link.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movieItem.link))
                startActivity(
                    binding.country.context,
                    browserIntent,
                    null
                )
            }
            binding.movieCard.setOnClickListener { onItemClickListener(bindingAdapterPosition) }
        }
    }


    companion object {
        private const val MOVIE_BASE_URL = "https://themoviedb.org/movie/"
        private const val POSTER_BASE_URL = "https://www.themoviedb.org/t/p/w220_and_h330_face/"
    }

}