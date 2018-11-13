package com.njp.smartlab.bean

data class ResourceResponseBody(
        val file_list: List<File>,
        val msg: String,
        val success: Boolean
)