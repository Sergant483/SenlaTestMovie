package com.example.senlatestmovie.presentation.fragment.extendedmovieinfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.senlatestmovie.data.database.entity.MovieModel
import com.example.senlatestmovie.data.usecase.GetMovieByIdUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ExtendedMoviePresenter :
    ExtendedMovieContract.Presenter, KoinComponent {
    private val getMovieByIdUseCase: GetMovieByIdUseCase by inject()
    private var view: ExtendedMovieContract.View? = null

    fun attachView(view: ExtendedMovieContract.View) {
        this.view = view
    }

    override suspend fun downloadData(id: Int): MovieModel = getMovieByIdUseCase.invoke(id)
    override fun openLink(context: Context,link:String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        context.startActivity(browserIntent)
    }

    fun detachView() {
        this.view = null
    }

}
