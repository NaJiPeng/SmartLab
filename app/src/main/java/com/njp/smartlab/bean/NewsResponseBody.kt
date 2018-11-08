package com.njp.smartlab.bean

data class NewsResponseBody(
        val msg: String,
        val news_list: List<News>,
        val success: Boolean
)