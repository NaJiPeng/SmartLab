package com.njp.smartlab.network

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 全局网络配置
 */
class NetworkConfig {

    private val client = OkHttpClient.Builder()
            .cookieJar(PersistentCookieJar(SetCookieCache(), MMKVCookiePersistor()))
            .build()

    val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://www.xxxx.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

}