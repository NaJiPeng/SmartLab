package com.njp.smartlab.ui.timetable

data class TimeTableEvent(val id: Int, val msg: String = "") {
    companion object {
        const val lessonsSuccess = 0
        const val lessonsFail = 1
    }
}