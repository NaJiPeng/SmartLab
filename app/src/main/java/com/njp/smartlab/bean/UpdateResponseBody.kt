package com.njp.smartlab.bean

data class UpdateResponseBody(
        val desc: String,
        val link: String,
        val msg: String,
        val size: String,
        val success: Boolean,
        val version_code: Int,
        val version_name: String
)