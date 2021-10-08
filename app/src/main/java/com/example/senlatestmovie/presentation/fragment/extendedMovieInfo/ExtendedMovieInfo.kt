package com.example.senlatestmovie.presentation.fragment.extendedMovieInfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.senlatestmovie.R
import com.example.senlatestmovie.api.models.ExtendedMovieInfo.ExtendedMovieInfoResponse
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.entity.MovieEntity
import com.example.senlatestmovie.databinding.ExtendedMovieInfoFragmentBinding
import com.example.senlatestmovie.databinding.MoviesFragmentBinding
import com.example.senlatestmovie.presentation.fragment.movie.adapter.MoviesAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExtendedMovieInfo : ScopeFragment() {

    private val viewModel: ExtendedMovieInfoViewModel by viewModel()
    private var _binding: ExtendedMovieInfoFragmentBinding? = null
    private val binding get() = _binding!!
    private var movie: MovieModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExtendedMovieInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments?.getLong(getString(R.string.bundle_movie_key))
        lifecycleScope.launch {
            movie = bundle?.let { viewModel.getMovieById(it) }
            Picasso.get().load(POSTER_BASE_URL + movie?.poster_path).into(binding.poster)
            binding.country.text = movie?.country
            binding.description.text = movie?.overview
            binding.releaseDate.text = movie?.release_date
                        binding.country.text = movie?.country
            movie?.link = MOVIE_BASE_URL + movie?.id
            binding.link.text = movie?.link
            binding.link.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie?.link))
                startActivity( browserIntent)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MOVIE_BASE_URL = "https://themoviedb.org/movie/"
        private const val POSTER_BASE_URL = "https://www.themoviedb.org/t/p/w220_and_h330_face/"
    }
}