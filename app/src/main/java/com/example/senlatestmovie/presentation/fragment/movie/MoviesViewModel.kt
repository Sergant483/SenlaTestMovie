package com.example.senlatestmovie.presentation.fragment.movie

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.graphics.Movie
import android.net.ConnectivityManager
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.database.AppDataBase
import com.example.senlatestmovie.data.usecase.*
import com.example.senlatestmovie.presentation.fragment.movie.paging.MoviePagingSource
import com.example.senlatestmovie.presentation.fragment.movie.paging.MovieRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoviesViewModel(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteAllMoviesUseCase: DeleteAllMoviesUseCase,
    private val saveAllMoviesUseCase: SaveAllMoviesUseCase,
    private val getRetrofitClientUseCase: GetRetrofitClientUseCase,
    private val getAllMoviesRetrofitUseCase: GetAllMoviesRetrofitUseCase,
    private val dataBase: AppDataBase
) : ViewModel() {
    private val _moviesList = MutableLiveData<List<MovieModel>>()
    val moviesList = _moviesList
    var saveInstanceStateRecyclerView: Parcelable? = null
//    val flow = Pager(PagingConfig(pageSize = 1000)) {
//        MoviePagingSource(getAllMoviesRetrofitUseCase)
//    }.flow.cachedIn(viewModelScope)

    private val pagingSourceFactory = { dataBase.movieDao.getAllDoggoModel() }//MoviePagingSource(getAllMoviesRetrofitUseCase) }

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(1000),
        remoteMediator = MovieRemoteMediator(
            dataBase,
            getAllMoviesRetrofitUseCase,
            saveAllMoviesUseCase
        ),
        pagingSourceFactory = pagingSourceFactory
    ).flow.cachedIn(viewModelScope)


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
            val data = getRetrofitClientUseCase.invoke().getMovieList(1)
            _moviesList.value = data.body()?.results!!
            _moviesList.value?.forEach {
                getExtendedMovieInfoList(it.id)
            }
            withContext(Dispatchers.Default) {
                //deleteAllMoviesUseCase.invoke()
                saveAllMoviesUseCase.invoke(_moviesList.value!!)
            }

        } catch (ex: Exception) {
            Log.e("ERROR", "NO internet connection -> $ex")
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