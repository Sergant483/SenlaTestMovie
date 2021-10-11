package com.example.senlatestmovie.api.models.popularMovie

import androidx.room.Ignore

data class MovieModel(
    var adult: Boolean = false,
    var backdrop_path: String = "",
    @Ignore
    var genre_ids: List<Int>? = emptyList(),
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
) {
    constructor() : this(
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
