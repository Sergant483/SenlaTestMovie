package com.example.senlatestmovie.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.senlatestmovie.data.database.entity.MovieModel

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieModel")
    suspend fun getAll(): List<MovieModel>

    @Query("SELECT * FROM MovieModel WHERE id = :id LIMIT 1")
    suspend fun getMovieById(id:Int): MovieModel

    @Query("SELECT * FROM MovieModel")
    fun getAllDoggoModel(): PagingSource<Int, MovieModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(MovieModel: List<MovieModel>)

    @Update
    suspend fun update(MovieModel: List<MovieModel>)

    @Query("DELETE  FROM MovieModel")
    suspend fun deleteAll()
}