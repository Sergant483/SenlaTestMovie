package com.example.senlatestmovie.api

import com.example.senlatestmovie.api.models.ExtendedMovieInfo.ExtendedMovieInfoResponse
import com.example.senlatestmovie.api.models.popularMovie.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitServices {

    @GET("3/movie/popular")
    suspend fun getMovieList(): Response<MoviesResponse>

    @GET("3/movie/{movie_id}?")
    suspend fun getExtendedMovieInfoList(@Path("movie_id") movieId: Int): Response<ExtendedMovieInfoResponse>
}