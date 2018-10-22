package com.njp.smartlab.network

import android.util.Log
import com.njp.smartlab.bean.LoginResponseBody
import com.njp.smartlab.utils.Logger
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

    fun login(id: String, pwdHash: String): Observable<LoginResponseBody> {
        Logger.getInstance().log("Repository:login")
        return service.login(id, pwdHash)
    }


}