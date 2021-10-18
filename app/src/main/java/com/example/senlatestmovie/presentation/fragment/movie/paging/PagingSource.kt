package com.example.senlatestmovie.presentation.fragment.movie.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.senlatestmovie.api.models.popularMovie.MovieModel
import com.example.senlatestmovie.data.usecase.GetAllMoviesRetrofitUseCase
import java.lang.Exception


class MoviePagingSource(private val getAllMoviesRetrofitUseCase: GetAllMoviesRetrofitUseCase) :
    PagingSource<Int, MovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        try {
            val nextPageNum = params.key ?: 1
            val response = getAllMoviesRetrofitUseCase.invoke(nextPageNum)
            return LoadResult.Page(data = response, prevKey = null, nextKey = nextPageNum + 1)
        } catch (ex: Exception) {
            return LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}