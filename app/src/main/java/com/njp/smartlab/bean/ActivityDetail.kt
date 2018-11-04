package com.njp.smartlab.bean

data class ActivityDetail(
        val activityDetailId: Int,
        val activityId: Int,
        val activityOrder: String,
        val day: String,
        val location: String,
        val week: String
)