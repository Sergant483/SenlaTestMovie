package com.example.senlatestmovie.data.datasource

import androidx.paging.PagingSource
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.AppDataBase
import com.example.senlatestmovie.data.database.entity.MovieEntity
import com.example.senlatestmovie.data.database.mapper.entityList
import com.example.senlatestmovie.data.database.mapper.modelList

internal interface MovieListDataSource {

    suspend fun loadAllMovies(): List<MovieModel>
    suspend fun insertAllMovies(movieList: List<MovieModel>)
    suspend fun deleteAll()
    suspend fun getMovieById(id: Int): MovieEntity
    //fun getDodoModel(): PagingSource<Int, MovieEntity>
}

internal class MovieListDataSourceImpl(private val db: AppDataBase) : MovieListDataSource {
    override suspend fun loadAllMovies(): List<MovieModel> = db.movieDao.getAll().modelList
    override suspend fun insertAllMovies(movieList: List<MovieModel>) {
        db.movieDao.insert(movieList.entityList)
    }

    override suspend fun deleteAll() {
        db.movieDao.deleteAll()
    }

    override suspend fun getMovieById(id: Int): MovieEntity = db.movieDao.getMovieById(id)

//    override fun getDodoModel(): PagingSource<Int, MovieEntity> =
//        db.movieDao.getAllDoggoModel()


}