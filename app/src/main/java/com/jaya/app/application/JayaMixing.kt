package com.jaya.app.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JayaMixing: Application(){


    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object{
        lateinit var  appInstance : JayaMixing
    }
}