package com.njp.smartlab.network

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 全局网络配置
 */
class NetworkConfig {

    companion object {
        private var instance: NetworkConfig? = null

        fun getInstance() = instance ?: NetworkConfig().apply { instance = this }

    }

    val client = OkHttpClient.Builder()
            .connectTimeout(6, TimeUnit.SECONDS)
            .writeTimeout(6, TimeUnit.SECONDS)
            .readTimeout(6, TimeUnit.SECONDS)
            .cookieJar(PersistentCookieJar(SetCookieCache(), MMKVCookiePersistor()))
            .build()

    val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://47.106.34.209:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()


}