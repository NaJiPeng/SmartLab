package com.njp.smartlab.bean

data class Lession(
        val activityDetails: List<ActivityDetail>,
        val activityId: Int,
        val createTime: Long,
        val maxNumber: Int,
        val name: String,
        val restNumber: Int,
        val status: Int,
        val teacher: String,
        val type: Int,
        val updateTime: Long
)