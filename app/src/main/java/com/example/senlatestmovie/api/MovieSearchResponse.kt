package com.example.senlatestmovie.api

import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<MovieModel> = emptyList(),
    val nextPage: Int? = null
)