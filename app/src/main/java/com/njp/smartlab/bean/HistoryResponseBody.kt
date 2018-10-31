package com.njp.smartlab.bean

data class HistoryResponseBody(
        val msg: String,
        val manipulations: List<Manipulation>,
        val success: Boolean
)