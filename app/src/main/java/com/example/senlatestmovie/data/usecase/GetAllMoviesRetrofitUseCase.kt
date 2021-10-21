package com.example.senlatestmovie.data.usecase

import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllMoviesRetrofitUseCase internal constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteAllMoviesUseCase: DeleteAllMoviesUseCase,
    private val saveAllMoviesUseCase: SaveAllMoviesUseCase,
    private val retrofitClientUseCase: GetRetrofitClientUseCase
) {
    private var moviesList = emptyList<MovieModel>()

    suspend operator fun invoke(pageNum:Int): List<MovieModel> {
        try {
            val data = retrofitClientUseCase.invoke().getMovieList(pageNum)
            moviesList = data.body()?.results!!
            moviesList.forEach {
                getExtendedMovieInfoList(it.id)
            }
            withContext(Dispatchers.Default) {
                //deleteAllMoviesUseCase.invoke()
                saveAllMoviesUseCase.invoke(moviesList)
            }
        } catch (ex: Exception) {
            moviesList = getAllMoviesUseCase.invoke()
            ex.printStackTrace()
        }
        return moviesList
    }

    private suspend fun getExtendedMovieInfoList(movieId: Int) {
        val data =
            retrofitClientUseCase.invoke().getExtendedMovieInfoList(
                movieId = movieId
            )
        moviesList.forEach {
            if (it.id == data.body()?.id) {
                var countries = ""
                data.body()?.production_countries?.forEach {
                    countries += it.name + DELIMETER
                }
                if (countries.isNotEmpty()) {
                    it.country = countries.substring(0, countries.length - 2)
                }
                it.link = MOVIE_BASE_URL + data.body()?.id
            }
        }
    }

    companion object {
        private const val DELIMETER: String = ", "
        private const val MOVIE_BASE_URL = "https://themoviedb.org/movie/"
    }
}
