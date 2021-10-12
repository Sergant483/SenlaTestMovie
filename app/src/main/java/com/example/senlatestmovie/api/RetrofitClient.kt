package com.example.senlatestmovie.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/"
    private const val API_KEY = "d557742cedd305383d77b2e73567b9b3"
    private val LANGUAGE = Locale.getDefault().language

    private var httpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", API_KEY)
                .addQueryParameter("language", LANGUAGE).build()
            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    })

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: RetrofitServices
        get() = retrofit.create(RetrofitServices::class.java)

}
