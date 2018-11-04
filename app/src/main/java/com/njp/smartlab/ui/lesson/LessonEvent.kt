package com.njp.smartlab.ui.lesson

/**
 * 注册界面事件约定接口
 */
data class LessonEvent(val id: Int, val msg: String = "") {
    companion object {
        const val lessonSuccess = 0
        const val lessonFail = 1
    }
}