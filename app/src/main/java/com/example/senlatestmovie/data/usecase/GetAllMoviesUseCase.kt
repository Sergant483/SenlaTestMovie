package com.example.senlatestmovie.data.usecase

import com.example.senlatestmovie.data.datasource.MovieListDataSource

class GetAllMoviesUseCase internal constructor(
    private val dataSource:MovieListDataSource
){
   suspend operator fun invoke() = dataSource.loadAllMovies()
}