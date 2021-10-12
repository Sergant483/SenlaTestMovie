package com.example.senlatestmovie.data.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    var movieId:Long = 0L,
    var adult: Boolean = false,
    var backdropPath: String = "",
    @Ignore
    var genreIds: List<Int>? = emptyList(),
    var id: Int = 0,
    var originalLanguage: String = "",
    var originalTitle: String = "",
    var overview: String = "",
    var popularity: Double = 0.0,
    var posterPath: String = "",
    var releaseDate: String = "",
    var title: String = "",
    @Ignore
    var video: Boolean = false,
    var voteAverage: Double = 0.0,
    var voteCount: Int = 0,
    var country: String = "",
    var link: String = "",
)