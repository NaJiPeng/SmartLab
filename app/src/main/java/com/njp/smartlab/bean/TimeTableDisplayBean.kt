package com.njp.smartlab.bean

import com.zhuangfei.timetable.model.Schedule

data class TimeTableDisplayBean(
        var isSuccess: Boolean = false,
        var msg: String = "",
        var curWeek: Int = 1,
        var data: List<Schedule>? = null
)