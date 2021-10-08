package com.example.senlatestmovie.data.database.mapper

internal interface IEntityModelMapper<T, R> {

    fun toModel(t: T): R
    fun toEntity(r: R): T
}
