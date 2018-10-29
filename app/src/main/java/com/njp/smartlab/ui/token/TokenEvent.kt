package com.njp.smartlab.ui.token

/**
 * 身份令牌界面事件约定接口
 */
data class TokenEvent(val id: Int, val msg: String = "") {
    companion object {
        const val tokenSuccess = 0
        const val tokenFail = 1
    }
}