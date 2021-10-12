package com.example.senlatestmovie.data.usecase

import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.datasource.MovieListDataSource

class SaveAllMoviesUseCase internal constructor(
    private val dataSource: MovieListDataSource
){
    suspend operator fun invoke(movieList:List<MovieModel>) = dataSource.insertAllMovies(movieList)
}