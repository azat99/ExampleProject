package com.example.example

import android.app.Application
import com.example.example.di.DaggerNewsComponent
import com.example.example.di.NewsComponent

class App : Application() {

    companion object {
        lateinit var newsComponent: NewsComponent
    }

    override fun onCreate() {
        super.onCreate()
        newsComponent = DaggerNewsComponent.builder().application(this).build()
    }
}