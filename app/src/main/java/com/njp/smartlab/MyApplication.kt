package com.njp.smartlab

import android.app.Application
import com.kingja.loadsir.core.LoadSir
import com.njp.smartlab.utils.loadsir.FailCallback
import com.njp.smartlab.utils.loadsir.LoadingCallback
import com.tencent.mmkv.MMKV
import com.uuzuche.lib_zxing.activity.ZXingLibrary

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

        ZXingLibrary.initDisplayOpinion(this)

        LoadSir.beginBuilder()
                .addCallback(LoadingCallback())
                .addCallback(FailCallback())
                .commit()
    }

}