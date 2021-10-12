package com.example.senlatestmovie.data.database.mapper

internal interface EntityModelMapper<T, R> {

    fun toModel(t: T): R
    fun toEntity(r: R): T
}
