package com.example.senlatestmovie.data.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    var adult: Boolean = false,
    var backdrop_path: String = "",
    @Ignore
    var genre_ids: List<Int>? = emptyList(),
    @PrimaryKey
    var id: Int = 0,
    var original_language: String = "",
    var original_title: String = "",
    var overview: String = "",
    var popularity: Double = 0.0,
    var poster_path: String = "",
    var release_date: String = "",
    var title: String = "",
    var video: Boolean = false,
    var vote_average: Double = 0.0,
    var vote_count: Int = 0,
    var country: String = "",
    var link: String = "",
)