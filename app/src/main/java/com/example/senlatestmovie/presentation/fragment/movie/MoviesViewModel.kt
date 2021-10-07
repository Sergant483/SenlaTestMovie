package com.example.senlatestmovie.presentation.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.senlatestmovie.api.Common
import com.example.senlatestmovie.api.RetrofitServices
import com.example.senlatestmovie.api.models.ExtendedMovieInfo.ExtendedMovieInfoResponse
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.api.models.popularMovie.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MoviesViewModel : ViewModel() {
    private val retrofitServices: RetrofitServices = Common.retrofitService
    private val _movieList = MutableLiveData<List<MovieModel>>()
    private var moviesList = emptyList<MovieModel>()
    val movieList: LiveData<List<MovieModel>> = _movieList


    fun getMovieList() {
        retrofitServices.getMovieList(apiKey = API_KEY, language = Locale.getDefault().language)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    moviesList = response.body()?.results!!
                    moviesList.forEach {
                        getExtendedMovieInfoList(it.id)
                    }
                }
            })
    }

    private fun getExtendedMovieInfoList(movieId: Int) {
        retrofitServices.getExtendedMovieInfoList(
            movieId = movieId,
            apiKey = API_KEY,
            language = Locale.getDefault().language
        ).enqueue(object : Callback<ExtendedMovieInfoResponse> {
            override fun onResponse(
                call: Call<ExtendedMovieInfoResponse>,
                response: Response<ExtendedMovieInfoResponse>
            ) {
                moviesList.forEach {
                    if (it.id == response.body()?.id) {
                        var countries = ""
                        response.body()?.production_countries?.forEach {
                            countries += it.name + DELIMETER
                        }
                        if (countries.isNotEmpty()) {
                            it.country = countries.substring(0, countries.length - 2)
                        }
                    }
                }
                _movieList.value = moviesList
            }

            override fun onFailure(call: Call<ExtendedMovieInfoResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    companion object {
        private const val DELIMETER: String = ", "
        private const val API_KEY = "d557742cedd305383d77b2e73567b9b3"
    }

}