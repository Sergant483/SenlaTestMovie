package com.example.senlatestmovie.presentation.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.senlatestmovie.R
import com.example.senlatestmovie.databinding.MoviesFragmentBinding
import com.example.senlatestmovie.presentation.fragment.extendedmovieinfo.ExtendedMovieInfo
import com.example.senlatestmovie.presentation.fragment.movie.adapter.MoviesAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : ScopeFragment() {

    private val viewModel: MoviesViewModel by viewModel()
    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MoviesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.moviesRecyclerView.layoutManager = layoutManager
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.moviesRecyclerView.adapter = adapter
        lifecycleScope.launch {
            initializeNetworkObserver()
            viewModel.pager.collectLatest { pagingData ->
                adapter.initialize(pagingData, ::onItemClick)
                adapter.submitData(pagingData)
            }
        }
        initSwipeRefreshListener()
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        try {
//            viewModel.saveInstanceStateRecyclerView =
//                binding.moviesRecyclerView.layoutManager?.onSaveInstanceState()
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//        super.onSaveInstanceState(outState)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(itemIndex: Int) {
        lifecycleScope.launch {
            val id = viewModel.getAllMoviesUseCase.invoke()[itemIndex].id
            val bundle = Bundle()
            id.let { bundle.putInt(ExtendedMovieInfo.BUNDLE_KEY, it) }
            findNavController().navigate(R.id.action_moviesFragment_to_extendedMovieInfo, bundle)
        }
    }


    private fun initSwipeRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener {
            if (!viewModel.isNetworkConnected(requireContext())) {
                Toast.makeText(requireContext(), "Нет интернета", Toast.LENGTH_LONG).show()
                binding.swipeRefresh.isRefreshing = false
            }
            viewModel.refresh()
            lifecycleScope.launch {
                viewModel.pager.collectLatest { pagingData ->
                    adapter.initialize(pagingData, ::onItemClick)
                    adapter.submitData(pagingData)
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private suspend fun initializeNetworkObserver() {
        while (!viewModel.isNetworkConnected(requireContext()) && viewModel.getAllMoviesUseCase.invoke()
                .isNullOrEmpty()
        ) {
            binding.noDataTextview.isVisible = true
            delay(1000)
            if (viewModel.isNetworkConnected(requireContext())) {
                binding.noDataTextview.isVisible = false
                viewModel.refresh()
            }
        }
    }


}
