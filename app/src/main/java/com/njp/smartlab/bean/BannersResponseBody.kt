package com.njp.smartlab.bean

data class BannersResponseBody(
        val banner_list: List<Banner>,
        val msg: String,
        val news_list: List<News>,
        val success: Boolean
)