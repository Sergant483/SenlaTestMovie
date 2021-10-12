package com.example.senlatestmovie.presentation.fragment.movie

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.usecase.DeleteAllMoviesUseCase
import com.example.senlatestmovie.data.usecase.GetAllMoviesUseCase
import com.example.senlatestmovie.data.usecase.GetRetrofitClientUseCase
import com.example.senlatestmovie.data.usecase.SaveAllMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MoviesViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteAllMoviesUseCase: DeleteAllMoviesUseCase,
    private val saveAllMoviesUseCase: SaveAllMoviesUseCase,
    private val getRetrofitClientUseCase: GetRetrofitClientUseCase
) : ViewModel() {
    var moviesList = emptyList<MovieModel>()


    private suspend fun getExtendedMovieInfoList(movieId: Int) {
        val data =
            getRetrofitClientUseCase.invoke().getExtendedMovieInfoList(
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

    suspend fun getMovieList(): List<MovieModel> {
        try {
            val data = getRetrofitClientUseCase.invoke().getMovieList()
            moviesList = data.body()?.results!!
            moviesList.forEach {
                getExtendedMovieInfoList(it.id)
            }
            withContext(Dispatchers.Default) {
                deleteAllMoviesUseCase.invoke()
                saveAllMoviesUseCase.invoke(moviesList)
            }

        } catch (ex: Exception) {
            moviesList = getAllMoviesUseCase.invoke()
            ex.printStackTrace()
        }
        return moviesList
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return (cm.activeNetworkInfo != null && cm.activeNetworkInfo?.isConnected == true)
    }

    companion object {
        private const val DELIMETER: String = ", "
        private const val MOVIE_BASE_URL = "https://themoviedb.org/movie/"
    }

}