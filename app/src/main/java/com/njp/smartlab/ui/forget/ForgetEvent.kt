package com.njp.smartlab.ui.forget

/**
 * 注册界面事件约定接口
 */
data class ForgetEvent(val id: Int, val msg: String = "") {
    companion object {
        const val emailSuccess = 0
        const val emailFail = 1
        const val forgetSuccess = 2
        const val forgetFail = 3
    }
}