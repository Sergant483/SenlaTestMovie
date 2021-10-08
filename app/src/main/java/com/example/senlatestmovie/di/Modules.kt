package com.example.senlatestmovie.di

import com.example.senlatestmovie.data.dataSource.IMovieListDataSource
import com.example.senlatestmovie.data.dataSource.MovieListDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.senlatestmovie.data.database.AppDataBase
import com.example.senlatestmovie.data.usecase.DeleteAllMoviesUseCase
import com.example.senlatestmovie.data.usecase.GetAllMoviesUseCase
import com.example.senlatestmovie.data.usecase.GetMovieByIdUseCase
import com.example.senlatestmovie.data.usecase.SaveAllMoviesUseCase
import com.example.senlatestmovie.presentation.fragment.extendedMovieInfo.ExtendedMovieInfo
import com.example.senlatestmovie.presentation.fragment.extendedMovieInfo.ExtendedMovieInfoViewModel
import com.example.senlatestmovie.presentation.fragment.movie.MoviesFragment
import com.example.senlatestmovie.presentation.fragment.movie.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

private val fragmentModule: Module = module {
    scope<MoviesFragment> {
        viewModel { MoviesViewModel(get(),get(),get()) }
    }
    scope<ExtendedMovieInfo> {
        viewModel { ExtendedMovieInfoViewModel(get()) }
    }
}

private val dataModule: Module = module {
    single { AppDataBase.newInstance(androidContext()) }
}

private val repositoryModule:Module = module {
    single<IMovieListDataSource> { MovieListDataSourceImpl(get()) }
}

private val useCaseModule:Module = module {
    factory { GetAllMoviesUseCase(get()) }
    factory { SaveAllMoviesUseCase(get()) }
    factory { DeleteAllMoviesUseCase(get()) }
    factory { GetMovieByIdUseCase(get()) }
}

val allModules = dataModule + fragmentModule + repositoryModule + useCaseModule