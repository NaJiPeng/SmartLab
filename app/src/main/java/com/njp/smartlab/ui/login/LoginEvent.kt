package com.njp.smartlab.ui.login

/**
 * 登录界面事件约定接口
 */
data class LoginEvent(val id: Int, val msg: String = "") {
    companion object {
        const val loginSuccess = 0
        const val loginFail = 1
        const val fillOut = 2
    }
}