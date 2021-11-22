package com.example.senlatestmovie.di

import com.example.senlatestmovie.api.RetrofitClient
import com.example.senlatestmovie.data.database.AppDataBase
import com.example.senlatestmovie.data.datasource.MovieListDataSource
import com.example.senlatestmovie.data.datasource.MovieListDataSourceImpl
import com.example.senlatestmovie.data.usecase.*
import com.example.senlatestmovie.feature.ISoundRecordFeature
import com.example.senlatestmovie.feature.SoundRecordFeature
import com.example.senlatestmovie.presentation.fragment.extendedmovieinfo.ExtendedMovieInfo
import com.example.senlatestmovie.presentation.fragment.extendedmovieinfo.ExtendedMovieInfoViewModel
import com.example.senlatestmovie.presentation.fragment.extendedmovieinfo.ExtendedMoviePresenter
import com.example.senlatestmovie.presentation.fragment.movie.MoviesFragment
import com.example.senlatestmovie.presentation.fragment.movie.MoviesViewModel
import com.example.senlatestmovie.presentation.fragment.music.MusicFragment
import com.example.senlatestmovie.presentation.fragment.music.MusicViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val fragmentModule: Module = module {
    scope<MoviesFragment> {
        viewModel { MoviesViewModel(get(), get(), get(), get()) }
    }
    scope<MusicFragment> {
        viewModel { MusicViewModel(get()) }
    }
    scope<ExtendedMovieInfo> {
        viewModel { ExtendedMovieInfoViewModel(get()) }
        factory { ExtendedMoviePresenter() }
    }

}

private val dataModule: Module = module {
    single { AppDataBase.newInstance(androidContext()) }
    single { RetrofitClient }
}

private val repositoryModule: Module = module {
    single<MovieListDataSource> { MovieListDataSourceImpl(get()) }
    single<ISoundRecordFeature> { SoundRecordFeature() }
}

private val useCaseModule: Module = module {
    factory { GetAllMoviesUseCase(get()) }
    factory { SaveAllMoviesUseCase(get()) }
    factory { DeleteAllMoviesUseCase(get()) }
    factory { GetMovieByIdUseCase(get()) }
    factory { GetRetrofitClientUseCase(get()) }
    factory { GetAllMoviesRetrofitUseCase(get()) }

}

val allModules = dataModule + fragmentModule + repositoryModule + useCaseModule