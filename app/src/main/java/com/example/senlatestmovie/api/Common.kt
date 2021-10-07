package com.example.senlatestmovie.api

object Common {
    private const val BASE_URL = "https://api.themoviedb.org/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}