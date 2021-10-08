package com.example.senlatestmovie.data.dataSource

import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.AppDataBase
import com.example.senlatestmovie.data.database.entity.MovieEntity
import com.example.senlatestmovie.data.database.mapper.entity
import com.example.senlatestmovie.data.database.mapper.entityList
import com.example.senlatestmovie.data.database.mapper.model
import com.example.senlatestmovie.data.database.mapper.modelList

internal interface IMovieListDataSource {

    suspend fun loadAllMovies(): List<MovieModel>
    suspend fun saveAllMovies(movieList: List<MovieModel>)
    suspend fun saveMovie(movie: MovieModel)
    suspend fun deleteAll()
    suspend fun getMovieById(id: Long): MovieEntity
}

internal class MovieListDataSourceImpl(private val db: AppDataBase) : IMovieListDataSource {
    override suspend fun loadAllMovies(): List<MovieModel> = db.movieDao.getAll().modelList
    override suspend fun saveAllMovies(movieList: List<MovieModel>) {
        db.save(movieList.entityList)
    }

    override suspend fun saveMovie(movie: MovieModel) {
        db.movieDao.insertOrUpdate(movie.entity)
    }

    override suspend fun deleteAll() {
        db.movieDao.deleteAll()
    }

    override suspend fun getMovieById(id: Long): MovieEntity = db.movieDao.getMovieById(id)

}