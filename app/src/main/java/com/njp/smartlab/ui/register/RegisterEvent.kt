package com.njp.smartlab.ui.register

/**
 * 注册界面事件约定接口
 */
data class RegisterEvent(val id: Int, val msg: String = "") {
    companion object {
        const val emailSuccess = 0
        const val emailFail = 1
        const val registerSuccess = 2
        const val registerFail = 3
    }
}