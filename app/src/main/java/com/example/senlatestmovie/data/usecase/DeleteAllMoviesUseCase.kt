package com.example.senlatestmovie.data.usecase

import com.example.senlatestmovie.data.dataSource.IMovieListDataSource

class DeleteAllMoviesUseCase internal constructor(
    private val dataSource: IMovieListDataSource
) {
    suspend operator fun invoke() = dataSource.deleteAll()
}