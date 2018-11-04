package com.njp.smartlab.bean

data class LockerResponseBody(
        val msg: String,
        val success: Boolean,
        val tools: List<Tool>
)