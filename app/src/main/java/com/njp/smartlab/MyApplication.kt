package com.njp.smartlab

import android.app.Application
import com.tencent.mmkv.MMKV

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
        MMKV.initialize(this)
    }

}