package com.example.senlatestmovie.presentation.fragment.extendedMovieInfo

import androidx.lifecycle.ViewModel
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.usecase.GetAllMoviesUseCase

class ExtendedMovieInfoViewModel(private val getAllMoviesUseCase: GetAllMoviesUseCase) :
    ViewModel() {

    suspend fun getMovieById(id: Long): MovieModel? {
        var movie: MovieModel? = null
        val movieList = getAllMoviesUseCase.invoke()
            movieList.forEach {
            if (it.id.toLong() == id)
                movie = it
        }
        return movie
    }
}