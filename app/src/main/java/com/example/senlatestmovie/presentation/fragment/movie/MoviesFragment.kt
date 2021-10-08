package com.example.senlatestmovie.presentation.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.senlatestmovie.R
import com.example.senlatestmovie.data.database.mapper.entity
import com.example.senlatestmovie.databinding.MoviesFragmentBinding
import com.example.senlatestmovie.presentation.fragment.movie.adapter.MoviesAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : ScopeFragment() {

    private val viewModel: MoviesViewModel by viewModel()
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
        lifecycleScope.launch {
            binding.moviesRecyclerView.adapter =
                MoviesAdapter(viewModel.getMovieList(), ::onItemClick)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(itemIndex: Int) {
        val id = viewModel.moviesList.get(itemIndex).id
        val bundle = Bundle()
        bundle.putLong(
            getString(R.string.bundle_movie_key),
            id.toLong()
        )
        findNavController().navigate(R.id.action_moviesFragment_to_extendedMovieInfo, bundle)
    }

}
