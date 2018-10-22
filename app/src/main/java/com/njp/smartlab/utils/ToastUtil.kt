package com.njp.smartlab.utils

import android.widget.Toast
import com.njp.smartlab.MyApplication

/**
 * 打Toast的工具类
 */
class ToastUtil private constructor() {

    companion object {
        private var instance: ToastUtil? = null

        fun getInstance() = instance ?: ToastUtil().apply { instance = this }
    }

    private val toast = Toast.makeText(MyApplication.instance, "", Toast.LENGTH_SHORT)

    fun show(content: String) {
        toast.apply {
            cancel()
            setText(content)
            show()
        }
    }

}