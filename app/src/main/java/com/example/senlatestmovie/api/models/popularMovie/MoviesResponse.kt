package com.example.senlatestmovie.api.models.popularMovie

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page:Int,
    val results:List<MovieModel>,
    @SerializedName("total_pages")
    val totalPages:Int,
    @SerializedName("total_results")
    val totalResults:Int
)
