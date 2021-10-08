package com.example.senlatestmovie.presentation.fragment.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.senlatestmovie.api.Common
import com.example.senlatestmovie.api.RetrofitServices
import com.example.senlatestmovie.api.models.ExtendedMovieInfo.ExtendedMovieInfoResponse
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.usecase.DeleteAllMoviesUseCase
import com.example.senlatestmovie.data.usecase.GetAllMoviesUseCase
import com.example.senlatestmovie.data.usecase.SaveAllMoviesUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*


class MoviesViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteAllMoviesUseCase: DeleteAllMoviesUseCase,
    private val saveAllMoviesUseCase: SaveAllMoviesUseCase
) : ViewModel() {
    private val retrofitServices: RetrofitServices = Common.retrofitService
    var moviesList = emptyList<MovieModel>()

    private fun getExtendedMovieInfoList(movieId: Int) {
        var data: Response<ExtendedMovieInfoResponse>? = null
        viewModelScope.launch {
            data =
                retrofitServices.getExtendedMovieInfoList(
                    movieId = movieId,
                    apiKey = API_KEY,
                    language = Locale.getDefault().language
                )
        }
        moviesList.forEach {
            if (it.id == data?.body()?.id) {
                var countries = ""
                data?.body()?.production_countries?.forEach {
                    countries += it.name + DELIMETER
                }
                if (countries.isNotEmpty()) {
                    it.country = countries.substring(0, countries.length - 2)
                }
            }
        }
    }

    suspend fun getMovieList(): List<MovieModel> {
        try {
            val data = retrofitServices.getMovieList(
                apiKey = API_KEY,
                language = Locale.getDefault().language
            )
            moviesList = data.body()?.results!!
            moviesList.forEach {
                getExtendedMovieInfoList(it.id)
            }
            deleteAllMoviesUseCase.invoke()
            saveAllMoviesUseCase.invoke(moviesList)
        } catch (ex: Exception) {
            moviesList = getAllMoviesUseCase.invoke()
            ex.printStackTrace()
        }
        return moviesList
    }

    companion object {
        private const val DELIMETER: String = ", "
        private const val API_KEY = "d557742cedd305383d77b2e73567b9b3"
    }

}