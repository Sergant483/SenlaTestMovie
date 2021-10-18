package com.example.senlatestmovie.presentation.fragment.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.databinding.MovieViewBinding
import com.squareup.picasso.Picasso

typealias OnItemClickListener = (position: Int) -> Unit

class MoviesAdapter() :
    PagingDataAdapter<MovieModel, MoviesAdapter.MoviesViewHolder>(diffCallback = MovieModelComparator) {
    private var moviesItems :PagingData<MovieModel>? = null
    private var onItemClickListener: OnItemClickListener? = null

    fun initialize(
        moviesItems: PagingData<MovieModel>,
        onItemClickListener: OnItemClickListener
    ) {
        this.moviesItems = moviesItems
        this.onItemClickListener = onItemClickListener
        notifyDataSetChanged()
    }

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
        getItem(position)?.let { holder.bind(it) } // moviesItems[position])
    }

    //override fun getItemCount(): Int = moviesItems.


    inner class MoviesViewHolder(private val binding: MovieViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieItem: MovieModel) {
            Picasso.get().load(POSTER_BASE_URL + movieItem.posterPath).into(binding.posterImage)
            binding.description.text = movieItem.overview
            binding.movieCard.setOnClickListener {
                onItemClickListener?.let { it1 ->
                    it1(
                        bindingAdapterPosition
                    )
                }
            }
        }
    }




    companion object {
        private const val POSTER_BASE_URL = "https://www.themoviedb.org/t/p/w220_and_h330_face/"
    }

}

object MovieModelComparator:DiffUtil.ItemCallback<MovieModel>(){
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }
}