package com.njp.smartlab.bean

data class LoginResponseBody(
        val msg: String,
        val success: Boolean,
        val user: User
)