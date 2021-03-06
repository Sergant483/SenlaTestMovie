package com.example.senlatestmovie.presentation.fragment.extendedmovieinfo

import androidx.lifecycle.ViewModel
import com.example.senlatestmovie.data.database.entity.MovieModel
import com.example.senlatestmovie.data.usecase.GetMovieByIdUseCase

class ExtendedMovieInfoViewModel(private val getMovieByIdUseCase: GetMovieByIdUseCase) :
    ViewModel() {

    suspend fun getMovieById(id: Int?): MovieModel? = id?.let { getMovieByIdUseCase.invoke(it) }

}