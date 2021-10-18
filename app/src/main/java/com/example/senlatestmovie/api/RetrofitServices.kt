package com.example.senlatestmovie.api

import com.example.senlatestmovie.api.models.ExtendedMovieInfo.ExtendedMovieInfoResponse
import com.example.senlatestmovie.api.models.popularMovie.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {

    @GET("3/movie/popular")
    suspend fun getMovieList(@Query("page") page: Int): Response<MoviesResponse>

    @GET("3/movie/{movie_id}?")
    suspend fun getExtendedMovieInfoList(@Path("movie_id") movieId: Int): Response<ExtendedMovieInfoResponse>
}