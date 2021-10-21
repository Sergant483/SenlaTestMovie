package com.example.senlatestmovie.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE id = :id LIMIT 1")
    suspend fun getMovieById(id:Int): MovieEntity

    @Query("SELECT * FROM MovieEntity")
    fun getAllDoggoModel(): PagingSource<Int, MovieModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movieEntity: List<MovieEntity>)

    @Update
    suspend fun update(movieEntity: List<MovieEntity>)

    @Query("DELETE  FROM MovieEntity")
    suspend fun deleteAll()
}