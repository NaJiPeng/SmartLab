package com.njp.smartlab

import android.app.Application

/**
 * Application实例
 */
class MyApplication : Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}