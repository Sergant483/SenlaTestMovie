package com.example.senlatestmovie.data.usecase

import com.example.senlatestmovie.data.datasource.MovieListDataSource

class DeleteAllMoviesUseCase internal constructor(
    private val dataSource: MovieListDataSource
) {
    suspend operator fun invoke() = dataSource.deleteAll()
}