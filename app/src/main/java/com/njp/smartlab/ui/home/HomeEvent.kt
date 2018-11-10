package com.njp.smartlab.ui.home

/**
 * 身份令牌界面事件约定接口
 */
data class HomeEvent(val id: Int, val msg: String = "") {
    companion object {
        const val updateSuccess = 0
        const val updateFail = 1
    }
}