package com.example.senlatestmovie

import android.app.Application
import com.example.senlatestmovie.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() = startKoin {
        androidContext(this@App)
        modules(allModules)
    }

}