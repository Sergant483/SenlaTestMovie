package com.example.senlatestmovie.api.models.popularMovie

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

data class MovieModel(
    var movieId:Long = 0L,
    var adult: Boolean = false,
    @SerializedName("backdrop_path")
    var backdropPath: String = "",
    @Ignore
    @SerializedName("genre_ids")
    var genreIds: List<Int>? = emptyList(),
    var id: Int = 0,
    @SerializedName("original_language")
    var originalLanguage: String = "",
    @SerializedName("original_title")
    var originalTitle: String = "",
    var overview: String = "",
    var popularity: Double = 0.0,
    @SerializedName("poster_path")
    var posterPath: String = "",
    @SerializedName("release_date")
    var releaseDate: String = "",
    var title: String = "",
    @Ignore
    var video: Boolean = false,
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    var voteCount: Int = 0,
    var country: String = "",
    var link: String = "",
) {
    constructor() : this(
        0L,
        false,
        "",
        emptyList(),
        0,
        "",
        "",
        "",
        0.0,
        "",
        "",
        "",
        false,
        0.0,
        0,
        "",
        ""
    )
}
