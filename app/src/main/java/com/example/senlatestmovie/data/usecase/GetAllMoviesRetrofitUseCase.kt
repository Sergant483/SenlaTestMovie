package com.example.senlatestmovie.data.usecase

import android.util.Log
import com.example.senlatestmovie.data.database.entity.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllMoviesRetrofitUseCase internal constructor(
    private val retrofitClientUseCase: GetRetrofitClientUseCase
) {
    private var moviesList = emptyList<MovieModel>()

    suspend operator fun invoke(pageNum: Int): List<MovieModel> {
        try {
            val data = retrofitClientUseCase.invoke().getMovieList(pageNum)
            moviesList = data.body()?.results!!
            Log.e("GGG","${data.body()?.results!!} $pageNum")
            moviesList.forEach {
                getExtendedMovieInfoList(it.id)
            }
            withContext(Dispatchers.Default) {
                //deleteAllMoviesUseCase.invoke()
                //saveAllMoviesUseCase.invoke(moviesList)
            }
        } catch (ex: Exception) {
            //moviesList = getAllMoviesUseCase.invoke()
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
