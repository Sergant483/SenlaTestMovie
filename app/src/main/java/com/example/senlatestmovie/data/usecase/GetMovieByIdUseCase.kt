package com.example.senlatestmovie.data.usecase

import com.example.senlatestmovie.data.datasource.MovieListDataSource

class GetMovieByIdUseCase internal constructor(
    private val dataSource: MovieListDataSource
) {
    suspend operator fun invoke(id: Int) = dataSource.getMovieById(id)
}