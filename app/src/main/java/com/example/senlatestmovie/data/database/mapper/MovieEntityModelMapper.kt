package com.example.senlatestmovie.data.database.mapper

import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.entity.MovieEntity

internal object MovieEntityModelMapper : EntityModelMapper<MovieEntity, MovieModel> {

    override fun toModel(movieEntity: MovieEntity): MovieModel = MovieModel(
        adult = movieEntity.adult,
        backdrop_path = movieEntity.backdrop_path,
        id = movieEntity.id,
        original_language = movieEntity.original_language,
        original_title = movieEntity.original_title,
        overview = movieEntity.overview,
        popularity = movieEntity.popularity,
        poster_path = movieEntity.poster_path,
        release_date = movieEntity.release_date,
        title = movieEntity.title,
        video = movieEntity.video,
        vote_average = movieEntity.vote_average,
        vote_count = movieEntity.vote_count,
        country = movieEntity.country,
        link = movieEntity.link,
    )

    override fun toEntity(movieModel: MovieModel): MovieEntity = MovieEntity(
        adult = movieModel.adult,
        backdrop_path = movieModel.backdrop_path,
        id = movieModel.id,
        original_language = movieModel.original_language,
        original_title = movieModel.original_title,
        overview = movieModel.overview,
        popularity = movieModel.popularity,
        poster_path = movieModel.poster_path,
        release_date = movieModel.release_date,
        title = movieModel.title,
        video = movieModel.video,
        vote_average = movieModel.vote_average,
        vote_count = movieModel.vote_count,
        country = movieModel.country,
        link = movieModel.link,
    )
}

internal val MovieEntity.model
    get() = MovieEntityModelMapper.toModel(
        this
    )

internal val MovieModel.entity
    get() = MovieEntityModelMapper.toEntity(
        this
    )

internal val List<MovieEntity>.modelList get() = map(MovieEntity::model)

internal val List<MovieModel>.entityList get() = map(MovieModel::entity)