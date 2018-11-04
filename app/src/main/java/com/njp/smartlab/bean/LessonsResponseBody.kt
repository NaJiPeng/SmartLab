package com.njp.smartlab.bean

data class LessonsResponseBody(
        val lessions: List<Lession>,
        val msg: String,
        val success: Boolean
)