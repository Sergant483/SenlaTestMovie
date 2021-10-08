package com.example.senlatestmovie.presentation.fragment.extendedMovieInfo

import androidx.lifecycle.ViewModel
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.entity.MovieEntity
import com.example.senlatestmovie.data.usecase.GetAllMoviesUseCase
import com.example.senlatestmovie.data.usecase.GetMovieByIdUseCase

class ExtendedMovieInfoViewModel(private val getAllMoviesUseCase: GetAllMoviesUseCase) :
    ViewModel() {

    suspend fun getMovieById(id: Long): MovieModel? {
        var trololo: MovieModel? = null
        getAllMoviesUseCase.invoke().forEach {
            if (it.id.toLong() == id)
                trololo = it
        }
        return trololo
    }

}