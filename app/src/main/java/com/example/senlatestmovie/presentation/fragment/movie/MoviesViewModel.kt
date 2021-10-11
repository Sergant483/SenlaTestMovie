package com.example.senlatestmovie.presentation.fragment.movie

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.example.senlatestmovie.api.Common
import com.example.senlatestmovie.api.RetrofitServices
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.usecase.DeleteAllMoviesUseCase
import com.example.senlatestmovie.data.usecase.GetAllMoviesUseCase
import com.example.senlatestmovie.data.usecase.SaveAllMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MoviesViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteAllMoviesUseCase: DeleteAllMoviesUseCase,
    private val saveAllMoviesUseCase: SaveAllMoviesUseCase
) : ViewModel() {
    private val retrofitServices: RetrofitServices = Common.retrofitService
    var moviesList = emptyList<MovieModel>()


    private suspend fun getExtendedMovieInfoList(movieId: Int) {
        val data =
            retrofitServices.getExtendedMovieInfoList(
                movieId = movieId,
                apiKey = API_KEY,
                language = LANGUAGE     //Locale.getDefault().language
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
            val data = retrofitServices.getMovieList(
                apiKey = API_KEY,
                language = LANGUAGE         //Locale.getDefault().language
            )
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
        private const val LANGUAGE: String = "en"
        private const val DELIMETER: String = ", "
        private const val API_KEY = "d557742cedd305383d77b2e73567b9b3"
        private const val MOVIE_BASE_URL = "https://themoviedb.org/movie/"
    }

}