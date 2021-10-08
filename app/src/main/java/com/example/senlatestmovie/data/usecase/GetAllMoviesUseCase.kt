package com.example.senlatestmovie.data.usecase

import com.example.senlatestmovie.data.dataSource.IMovieListDataSource

class GetAllMoviesUseCase internal constructor(
    private val dataSource:IMovieListDataSource
){
   suspend operator fun invoke() = dataSource.loadAllMovies()
}