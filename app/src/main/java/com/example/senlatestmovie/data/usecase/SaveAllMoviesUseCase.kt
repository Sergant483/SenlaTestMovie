package com.example.senlatestmovie.data.usecase

import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.dataSource.IMovieListDataSource

class SaveAllMoviesUseCase internal constructor(
    private val dataSource: IMovieListDataSource
){
    suspend operator fun invoke(movieList:List<MovieModel>) = dataSource.saveAllMovies(movieList)
}