package com.njp.smartlab.ui.update

/**
 * 信息修改界面事件约定接口
 */
data class UpdateEvent(val id: Int, val msg: String = "") {
    companion object {
        const val updateSuccess = 0
        const val updateFail = 1
    }
}