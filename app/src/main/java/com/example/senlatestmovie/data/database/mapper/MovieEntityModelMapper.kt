package com.example.senlatestmovie.data.database.mapper

import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.entity.MovieEntity

internal object MovieEntityModelMapper : IEntityModelMapper<MovieEntity, MovieModel> {

    override fun toModel(t: MovieEntity): MovieModel = MovieModel(
        adult = t.movie.adult,
        backdrop_path = t.movie.backdrop_path,
        id = t.movie.id,
        original_language = t.movie.original_language,
        original_title = t.movie.original_title,
        overview = t.movie.overview,
        popularity = t.movie.popularity,
        poster_path = t.movie.poster_path,
        release_date = t.movie.release_date,
        title = t.movie.title,
        video = t.movie.video,
        vote_average = t.movie.vote_average,
        vote_count = t.movie.vote_count,
        country = t.movie.country,
        link = t.movie.link,
    )

    override fun toEntity(r: MovieModel): MovieEntity = MovieEntity(
        movie = r
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