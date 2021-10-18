package com.example.senlatestmovie.presentation.fragment.extendedmovieinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.senlatestmovie.databinding.ExtendedMovieInfoFragmentBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment

class ExtendedMovieInfo : ScopeFragment(), ExtendedMovieContract.View {

    //    private val viewModel: ExtendedMovieInfoViewModel by viewModel()
    private var _binding: ExtendedMovieInfoFragmentBinding? = null
    private val binding get() = _binding!!
    private var bundle: Int? = null
    private val presenter: ExtendedMoviePresenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExtendedMovieInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle = arguments?.getInt(BUNDLE_KEY)
        presenter.attachView(view = this)
        showUIData()
    }

    override fun showUIData() {
        lifecycleScope.launch {
            val movie = bundle?.let { presenter.downloadData(it) }
            Picasso.get().load(POSTER_BASE_URL + movie?.posterPath).into(binding.poster)
            binding.country.text = movie?.country
            binding.description.text = movie?.overview
            binding.releaseDate.text = movie?.releaseDate
            binding.country.text = movie?.country
            binding.link.text = movie?.link
            binding.link.setOnClickListener {
                movie?.link?.let { it1 -> presenter.openLink(requireContext(), it1) }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.detachView()
    }

    companion object {
        const val BUNDLE_KEY = "KEY"
        private const val POSTER_BASE_URL = "https://www.themoviedb.org/t/p/w220_and_h330_face/"
    }
}