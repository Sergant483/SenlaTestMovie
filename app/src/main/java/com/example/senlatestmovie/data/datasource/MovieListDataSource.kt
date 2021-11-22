package com.example.senlatestmovie.data.datasource

import com.example.senlatestmovie.data.database.AppDataBase
import com.example.senlatestmovie.data.database.entity.MovieModel

internal interface MovieListDataSource {

    suspend fun loadAllMovies(): List<MovieModel>
    suspend fun insertAllMovies(movieList: List<MovieModel>)
    suspend fun deleteAll()
    suspend fun getMovieById(id: Int): MovieModel

}

internal class MovieListDataSourceImpl(private val db: AppDataBase) : MovieListDataSource {
    override suspend fun loadAllMovies(): List<MovieModel> = db.movieDao.getAll()
    override suspend fun insertAllMovies(movieList: List<MovieModel>) {
        db.movieDao.insert(movieList)
    }

    override suspend fun deleteAll() {
        db.movieDao.deleteAll()
    }

    override suspend fun getMovieById(id: Int): MovieModel = db.movieDao.getMovieById(id)

}