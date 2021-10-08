package com.example.senlatestmovie.data.database.dao

import androidx.room.*
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE movieId = :id LIMIT 1")
    suspend fun getMovieById(id:Long): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(movieEntity: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(movieEntity: MovieEntity)

    @Query("DELETE  FROM MovieEntity")
    suspend fun deleteAll()
}