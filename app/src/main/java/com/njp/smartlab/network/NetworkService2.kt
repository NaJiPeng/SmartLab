package com.njp.smartlab.network

import com.njp.smartlab.bean.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * 网络请求接口
 */
interface NetworkService2 {

    @GET("/api/v1.0/banners")
    fun getBannerNews(): Observable<BannersResponseBody>

    @GET("/api/v1.0/news")
    fun getNews(
            @Query("page") page: Int
    ): Observable<NewsResponseBody>

    @GET("/api/v1.0/{type}")
    fun getDetail(
            @Path("type") type: String,
            @Query("url") url: String
    ): Observable<DetailResponseBody>

    @GET("/api/v1.0/version")
    fun update(): Observable<UpdateResponseBody>

}