package com.njp.smartlab.ui.history

/**
 * 注册界面事件约定接口
 */
data class HistoryEvent(val id: Int, val msg: String = "") {
    companion object {
        const val refreshSuccess = 0
        const val refreshFail = 1
    }
}