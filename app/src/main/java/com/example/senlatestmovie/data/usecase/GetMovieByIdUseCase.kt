package com.example.senlatestmovie.data.usecase

import com.example.senlatestmovie.data.dataSource.IMovieListDataSource

class GetMovieByIdUseCase internal constructor(
    private val dataSource: IMovieListDataSource
){
    suspend operator fun invoke(id:Long) = dataSource.getMovieById(id)
}