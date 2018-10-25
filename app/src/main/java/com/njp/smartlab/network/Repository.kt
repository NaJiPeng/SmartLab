package com.njp.smartlab.network

import com.njp.smartlab.bean.LoginResponseBody
import io.reactivex.Observable


/**
 * 应用网络请求唯一入口
 */
class Repository private constructor() {

    companion object {
        private var instance: Repository? = null

        fun getInstance() = instance ?: Repository().apply { instance = this }
    }

    private val service = NetworkConfig.getInstance().retrofit.create(NetworkService::class.java)

    /**
     * 登录
     */
    fun login(id: String, pwdHash: String) = service.login(id, pwdHash)

    /**
     * 注册验证码
     */
    fun verifyEmail(email: String) = service.verifyEmail(email)

    /**
     * 注册
     */
    fun register(
            userId: String, pwdHash: String, email: String, name: String, captcha: String
    ) = service.register(
            userId, pwdHash, email, name, captcha
    )

}