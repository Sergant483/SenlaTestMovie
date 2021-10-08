package com.example.senlatestmovie.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.senlatestmovie.api.models.popularMovie.MovieModel

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val movieId: Long = 0L,
    @Embedded
    val movie: MovieModel = MovieModel()
)