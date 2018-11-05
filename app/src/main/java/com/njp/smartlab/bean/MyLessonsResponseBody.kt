package com.njp.smartlab.bean

data class MyLessonsResponseBody(
        val currWeek: Int,
        val lessions: List<LessionX>,
        val msg: String,
        val success: Boolean
)