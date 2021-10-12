package com.example.senlatestmovie.presentation.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.senlatestmovie.R
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.databinding.MoviesFragmentBinding
import com.example.senlatestmovie.presentation.fragment.extendedmovieinfo.ExtendedMovieInfo
import com.example.senlatestmovie.presentation.fragment.movie.adapter.MoviesAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : ScopeFragment() {

    private val viewModel: MoviesViewModel by viewModel()
    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!
    var moviesList: List<MovieModel>? = null


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
        lifecycleScope.launch {
            initializeNetworkObserver()
            initializeMovieListObserver()
            viewModel.getMovieList()
        }
        initSwipeRefreshListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveInstanceStateRecyclerView =
            binding.moviesRecyclerView.layoutManager?.onSaveInstanceState()
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(itemIndex: Int) {
        val id = viewModel.moviesList.value?.get(itemIndex)?.id
        val bundle = Bundle()
        id?.let { bundle.putInt(ExtendedMovieInfo.BUNDLE_KEY, it) }
        viewModel.saveInstanceStateRecyclerView =
            binding.moviesRecyclerView.layoutManager?.onSaveInstanceState()
        findNavController().navigate(R.id.action_moviesFragment_to_extendedMovieInfo, bundle)
    }

    private fun initSwipeRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launchWhenStarted {
                viewModel.getMovieList()
                binding.moviesRecyclerView.adapter =
                    viewModel.moviesList.value?.let { MoviesAdapter(it, ::onItemClick) }
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private suspend fun initializeNetworkObserver() {
        while (!viewModel.isNetworkConnected(requireContext()) && viewModel.moviesList.value.isNullOrEmpty()) {
            binding.noDataTextview.isVisible = true
            delay(1000)
            if (viewModel.isNetworkConnected(requireContext())) {
                binding.noDataTextview.isVisible = false
                viewModel.getMovieList()
            }
        }
    }

    private fun initializeMovieListObserver() {
        viewModel.moviesList.observe(viewLifecycleOwner, { moviesList ->
            binding.moviesRecyclerView.adapter =
                MoviesAdapter(moviesList, ::onItemClick)
            binding.moviesRecyclerView.layoutManager?.onRestoreInstanceState(viewModel.saveInstanceStateRecyclerView)
        })
    }

}
