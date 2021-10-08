package com.example.senlatestmovie.data.dataSource

import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.AppDataBase
import com.example.senlatestmovie.data.database.mapper.entity
import com.example.senlatestmovie.data.database.mapper.entityList
import com.example.senlatestmovie.data.database.mapper.modelList

internal interface IMovieListDataSource {

    suspend fun loadAllMovies(): List<MovieModel>
    suspend fun saveAllMovies(movieList: List<MovieModel>)
    suspend fun saveMovie(movie: MovieModel)
}

internal class MovieListDataSourceImpl(private val db: AppDataBase) : IMovieListDataSource {
    override suspend fun loadAllMovies(): List<MovieModel> = db.movieDao.getAll().modelList
    override suspend fun saveAllMovies(movieList: List<MovieModel>) {
        db.save(movieList.entityList)
    }
    override suspend fun saveMovie(movie: MovieModel) {
        db.movieDao.insertOrUpdate(movie.entity)
    }
}