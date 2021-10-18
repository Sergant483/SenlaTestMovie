package com.example.senlatestmovie.data.usecase

import com.example.senlatestmovie.api.RetrofitClient

class GetRetrofitClientUseCase(
    private val retrofitClient: RetrofitClient
) {
    operator fun invoke() = retrofitClient.retrofitService
}