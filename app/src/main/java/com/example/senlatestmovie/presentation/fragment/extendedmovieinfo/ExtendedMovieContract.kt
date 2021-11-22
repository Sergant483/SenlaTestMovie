package com.example.senlatestmovie.presentation.fragment.extendedmovieinfo

import android.content.Context
import com.example.senlatestmovie.data.database.entity.MovieModel

class ExtendedMovieContract {
    interface View{
        fun showUIData()
    }
    interface Presenter{
        suspend fun downloadData(id:Int): MovieModel
        fun openLink(context: Context,link:String)
    }
}