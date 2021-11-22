package com.example.senlatestmovie.presentation.fragment.movie

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.senlatestmovie.data.database.AppDataBase
import com.example.senlatestmovie.data.usecase.*
import com.example.senlatestmovie.presentation.fragment.movie.paging.MoviePagingSource
import com.example.senlatestmovie.presentation.fragment.movie.paging.MovieRemoteMediator


class MoviesViewModel(
    val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val saveAllMoviesUseCase: SaveAllMoviesUseCase,
    val getAllMoviesRetrofitUseCase: GetAllMoviesRetrofitUseCase,
    private val dataBase: AppDataBase
) : ViewModel() {

    val flow = Pager(PagingConfig(pageSize = 2)) {
        MoviePagingSource(getAllMoviesRetrofitUseCase)
    }.flow.cachedIn(viewModelScope)

    private val pagingSourceFactory = { dataBase.movieDao.getAllDoggoModel() }

    @OptIn(ExperimentalPagingApi::class)
    var pager = Pager(
        config = PagingConfig(2),
        remoteMediator = MovieRemoteMediator(
            dataBase,
            getAllMoviesRetrofitUseCase,
            saveAllMoviesUseCase
        ),
        pagingSourceFactory = pagingSourceFactory
    ).flow.cachedIn(viewModelScope)

    @OptIn(ExperimentalPagingApi::class)
    fun refresh() {
        pager = Pager(
            config = PagingConfig(2),
            remoteMediator = MovieRemoteMediator(
                dataBase,
                getAllMoviesRetrofitUseCase,
                saveAllMoviesUseCase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.cachedIn(viewModelScope)
    }


    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return (cm.activeNetworkInfo != null && cm.activeNetworkInfo?.isConnected == true)
    }
}