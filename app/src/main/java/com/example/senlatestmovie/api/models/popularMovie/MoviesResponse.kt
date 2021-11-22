package com.example.senlatestmovie.api.models.popularMovie

import com.example.senlatestmovie.data.database.entity.MovieModel
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page:Int = 0,
    val results:List<MovieModel> = emptyList(),
    @SerializedName("total_pages")
    val totalPages:Int = 0,
    @SerializedName("total_results")
    val totalResults:Int = 0
)
