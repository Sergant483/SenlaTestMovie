package com.example.senlatestmovie.presentation.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.senlatestmovie.R
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.usecase.GetAllMoviesUseCase
import com.example.senlatestmovie.data.usecase.SaveAllMoviesUseCase
import com.example.senlatestmovie.databinding.MoviesFragmentBinding
import com.example.senlatestmovie.presentation.fragment.movie.adapter.MoviesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : ScopeFragment() {

    private val viewModel: MoviesViewModel by viewModel()
    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!
    private val getAllMoviesUseCase: GetAllMoviesUseCase by inject()
    private val saveAllMoviesUseCase: SaveAllMoviesUseCase by inject()

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
        if (viewModel.movieList.value.isNullOrEmpty()) {
            viewModel.getMovieList()
            lifecycleScope.launch {
                binding.moviesRecyclerView.adapter =
                    MoviesAdapter(getAllMoviesUseCase.invoke(), ::onItemClick)
            }
        }
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
                lifecycleScope.launch(Dispatchers.Default) {
                    movieModel?.let {
                        saveAllMoviesUseCase.invoke(it)
                    }
                }
                binding.moviesRecyclerView.adapter =
                    movieModel?.let { MoviesAdapter(it, ::onItemClick) }
            }
        })
    }

}