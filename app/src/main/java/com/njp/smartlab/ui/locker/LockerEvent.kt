package com.njp.smartlab.ui.locker

/**
 * 注册界面事件约定接口
 */
data class LockerEvent(val id: Int, val msg: String = "") {
    companion object {
        const val lockerSuccess = 0
        const val lockerFail = 1
    }
}