package com.beetech.hsba

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {


    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext;

    }


}
