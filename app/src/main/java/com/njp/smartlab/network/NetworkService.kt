package com.njp.smartlab.network

import com.njp.smartlab.bean.LoginResponseBody
import com.njp.smartlab.bean.ResponseBody
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


    @POST("/laboratory/front/user/verifyemail")
    @FormUrlEncoded
    fun verifyEmail(
            @Field("email") id: String
    ): Observable<ResponseBody>


    @POST("/laboratory/front/user/register")
    @FormUrlEncoded
    fun register(
            @Field("userId") userId: String,
            @Field("pwdHash") pwdHash: String,
            @Field("email") email: String,
            @Field("name") name: String,
            @Field("captcha") captcha: String
    ): Observable<ResponseBody>

    @POST("/laboratory/front/user/changepwdverify")
    @FormUrlEncoded
    fun changePwdVerify(
            @Field("email") email: String
    ): Observable<ResponseBody>


    @POST("/laboratory/front/user/updatepwd")
    @FormUrlEncoded
    fun updatePwd(
            @Field("captcha") captcha: String,
            @Field("newPwd") newPwd: String,
            @Field("email") email: String,
            @Field("id") id: String
    ): Observable<ResponseBody>

    @POST("/laboratory/front/user/update")
    @FormUrlEncoded
    fun update(
            @Field("name") name: String,
            @Field("major") major: String
    ): Observable<LoginResponseBody>


}