package com.njp.smartlab.utils

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.njp.smartlab.bean.User
import com.tencent.mmkv.MMKV

/**
 * 用户信息持久化类
 */
class UserInfoHolder private constructor() {

    companion object {
        private var instance: UserInfoHolder? = null

        fun getInstance() = instance ?: UserInfoHolder().apply { instance = this }
    }

    private val gson = Gson()
    private var user = MutableLiveData<User>().apply {
        value = MMKV.defaultMMKV().decodeString("user_info")?.let {
            return@let gson.fromJson(it, User::class.java)
        }
    }

    fun getUser() = user

    fun saveUser(user: User) {
        this.user.value = user
        MMKV.defaultMMKV().encode("user_info", gson.toJson(user))
    }

    fun clearUser() {
        this.user.value = null
        MMKV.defaultMMKV().removeValueForKey("user_info")
    }

}