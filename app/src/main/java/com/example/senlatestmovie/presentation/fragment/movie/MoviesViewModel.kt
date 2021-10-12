package com.example.senlatestmovie.presentation.fragment.movie

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MoviesViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteAllMoviesUseCase: DeleteAllMoviesUseCase,
    private val saveAllMoviesUseCase: SaveAllMoviesUseCase,
    private val getRetrofitClientUseCase: GetRetrofitClientUseCase
) : ViewModel() {
    private val _moviesList =  MutableLiveData<List<MovieModel>>()
    val moviesList = _moviesList
    var saveInstanceStateRecyclerView:Parcelable? = null
    //var moviesList = emptyList<MovieModel>()


    private suspend fun getExtendedMovieInfoList(movieId: Int) {
        val data =
            getRetrofitClientUseCase.invoke().getExtendedMovieInfoList(
                movieId = movieId
            )
        _moviesList.value?.forEach {
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

    suspend fun getMovieList() {
        try {
            _moviesList.value = getAllMoviesUseCase.invoke()
            val data = getRetrofitClientUseCase.invoke().getMovieList()
            _moviesList.value = data.body()?.results!!
            _moviesList.value?.forEach {
                getExtendedMovieInfoList(it.id)
            }
            withContext(Dispatchers.Default) {
                deleteAllMoviesUseCase.invoke()
                saveAllMoviesUseCase.invoke(_moviesList.value!!)
            }

        } catch (ex: Exception) {
            //_moviesList.value = getAllMoviesUseCase.invoke()
            ex.printStackTrace()
        }

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