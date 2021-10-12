package com.example.senlatestmovie.data.database.mapper

import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.entity.MovieEntity

internal object MovieEntityModelMapper : EntityModelMapper<MovieEntity, MovieModel> {

    override fun toModel(movieEntity: MovieEntity): MovieModel = MovieModel(
        movieId = movieEntity.movieId,
        adult = movieEntity.adult,
        backdropPath = movieEntity.backdropPath,
        id = movieEntity.id,
        originalLanguage = movieEntity.originalLanguage,
        originalTitle = movieEntity.originalTitle,
        overview = movieEntity.overview,
        popularity = movieEntity.popularity,
        posterPath = movieEntity.posterPath,
        releaseDate = movieEntity.releaseDate,
        title = movieEntity.title,
        voteAverage = movieEntity.voteAverage,
        voteCount = movieEntity.voteCount,
        country = movieEntity.country,
        link = movieEntity.link,
    )

    override fun toEntity(movieModel: MovieModel): MovieEntity = MovieEntity(
        movieId = movieModel.movieId,
        adult = movieModel.adult,
        backdropPath = movieModel.backdropPath,
        id = movieModel.id,
        originalLanguage = movieModel.originalLanguage,
        originalTitle = movieModel.originalTitle,
        overview = movieModel.overview,
        popularity = movieModel.popularity,
        posterPath = movieModel.posterPath,
        releaseDate = movieModel.releaseDate,
        title = movieModel.title,
        voteAverage = movieModel.voteAverage,
        voteCount = movieModel.voteCount,
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