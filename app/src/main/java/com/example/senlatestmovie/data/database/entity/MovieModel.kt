package com.example.senlatestmovie.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieModel(
    @PrimaryKey(autoGenerate = true)
    val movieId: Long = 0L,
    @SerializedName("adult") val adult: Boolean = false,
    @SerializedName("backdrop_path") val backdropPath: String = "",
//    @Ignore
//    val genreIds: List<Int>? = emptyList(),
    @SerializedName("id") val id: Int = 0,
    @SerializedName("original_language") val originalLanguage: String = "",
    @SerializedName("original_title") val originalTitle: String = "",
    @SerializedName("overview") val overview: String = "",
    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("title") val title: String = "",
//    @Ignore
//    val video: Boolean = false,
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0,
    var country: String = "",
    var link: String = "",
)

