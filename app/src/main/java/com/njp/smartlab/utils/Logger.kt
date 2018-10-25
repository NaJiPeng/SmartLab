package com.njp.smartlab.utils

import android.util.Log

/**
 * 打Log的工具类
 */
class Logger private constructor() {

    companion object {

        private const val switch = true

        private const val tag = "wwww"

        private var instance: Logger? = null

        fun getInstance() = instance ?: Logger().apply { instance = this }

    }

    fun log(msg: String?) {
        if (switch) Log.i(tag, msg)
    }

    fun error(msg: String?, throwable: Throwable) {
        if (switch) Log.e(tag, msg, throwable)
    }

}