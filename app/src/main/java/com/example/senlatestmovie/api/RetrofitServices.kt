package com.example.senlatestmovie.api

import com.example.senlatestmovie.api.models.ExtendedMovieInfo.ExtendedMovieInfoResponse
import com.example.senlatestmovie.api.models.popularMovie.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {

    @GET("3/movie/popular")
    fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<MoviesResponse>

    @GET("3/movie/{movie_id}?")
    fun getExtendedMovieInfoList(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<ExtendedMovieInfoResponse>
}