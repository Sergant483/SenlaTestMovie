package com.example.senlatestmovie.presentation.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.senlatestmovie.R
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.databinding.MoviesFragmentBinding
import com.example.senlatestmovie.presentation.fragment.movie.adapter.MoviesAdapter

class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        if (viewModel.movieList.value.isNullOrEmpty()) viewModel.getMovieList()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(itemIndex: Int) {
        val bundle = Bundle()
        viewModel.movieList.value?.get(itemIndex)?.id?.let {
            bundle.putInt(
                getString(R.string.bundle_movie_key),
                it
            )
        }
        findNavController().navigate(R.id.action_moviesFragment_to_extendedMovieInfo, bundle)
    }

    private fun initObservers() {
        viewModel.movieList.observe(viewLifecycleOwner, object :
            Observer<List<MovieModel>> {
            override fun onChanged(movieModel: List<MovieModel>?) {
                binding.moviesRecyclerView.adapter =
                    movieModel?.let { MoviesAdapter(it, ::onItemClick) }
            }
        })
    }

}