package com.njp.smartlab.network

import com.njp.smartlab.bean.LoginResponseBody
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 网络请求接口
 */
interface NetworkService {

    @POST("laboratory/front/user/login")
    @FormUrlEncoded
    fun login(
            @Field("id") id: String,
            @Field("pwdHash") pwdHash: String
    ): Observable<LoginResponseBody>

}