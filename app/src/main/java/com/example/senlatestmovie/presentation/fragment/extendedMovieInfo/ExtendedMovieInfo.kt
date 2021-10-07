package com.example.senlatestmovie.presentation.fragment.extendedMovieInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.senlatestmovie.R

class ExtendedMovieInfo : Fragment() {

    private lateinit var viewModel: ExtendedMovieInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.extended_movie_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments?.getInt(getString(R.string.bundle_movie_key))
        Log.d("TAGG", "$bundle")
    }


}